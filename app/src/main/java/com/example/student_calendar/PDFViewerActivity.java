package com.example.student_calendar;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFViewerActivity extends AppCompatActivity {

        private WebView pdfWebView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pdfviewer);

            pdfWebView = findViewById(R.id.pdfWebView);

            // Enable JavaScript in the web view
            WebSettings webSettings = pdfWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            String pdfUrl = getIntent().getStringExtra("filePath");
            pdfWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.endsWith(".pdf")) {
                        // Load PDF URL
                        view.loadUrl("https://docs.google.com/gview?embedded=true&url=" + pdfUrl);
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }
            });
            pdfWebView.loadUrl(pdfUrl);
            //String pdfUrl = getIntent().getStringExtra("filePath");
            //pdfWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+pdfUrl);

            // Set a WebViewClient to handle the URL loading
            //pdfWebView.setWebViewClient(new WebViewClient());
        }
}