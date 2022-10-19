package navayuga.com;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;

import java.util.HashMap;


public class HomeActivity extends  AppCompatActivity  { 
	
	
	private RelativeLayout linear1;
	private SwipeRefreshLayout swiperefreshlayout1;
	private LinearLayout no_internet;
	private LinearLayout progress;
	private WebView webview2;
	private LinearLayout linear2;
	private TextView textview3;
	private TextView textview1;
	private Button button1;
	private LottieAnimationView lottie1;
	
	private RequestNetwork internet;
	private RequestNetwork.RequestListener _internet_request_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = findViewById(R.id.linear1);
		swiperefreshlayout1 = findViewById(R.id.swiperefreshlayout1);
		no_internet = findViewById(R.id.no_internet);
		progress = findViewById(R.id.progress);
		webview2 = findViewById(R.id.webview2);
		webview2.getSettings().setJavaScriptEnabled(true);
		webview2.getSettings().setSupportZoom(true);
		linear2 = findViewById(R.id.linear2);
		textview3 = findViewById(R.id.textview3);
		textview1 = findViewById(R.id.textview1);
		button1 = findViewById(R.id.button1);
		lottie1 = findViewById(R.id.lottie1);
		internet = new RequestNetwork(this);
		
		webview2.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				progress.setVisibility(View.VISIBLE);
				no_internet.setVisibility(View.GONE);
				internet.startRequestNetwork(RequestNetworkController.GET, _url, "null", _internet_request_listener);
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				progress.setVisibility(View.GONE);
				if (Util.isConnected(getApplicationContext())) {
					swiperefreshlayout1.setVisibility(View.VISIBLE);
				}
				super.onPageFinished(_param1, _param2);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				webview2.loadUrl(webview2.getUrl());
			}
		});
		
		_internet_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				no_internet.setVisibility(View.GONE);
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				no_internet.setVisibility(View.VISIBLE);
				textview1.setText(_message);
				swiperefreshlayout1.setVisibility(View.GONE);
			}
		};
	}
	
	private void initializeLogic() {


		webview2.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webview2.loadUrl("https://odishaddn.com/");
		no_internet.setVisibility(View.GONE);
		swiperefreshlayout1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				webview2.loadUrl(webview2.getUrl());
				swiperefreshlayout1.setRefreshing(false);
			}
		});
		_WebView(true, true, true, true, true, webview2);
		_youtube_fullscreen_webview_2(webview2);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

	}
	
	@Override
	public void onBackPressed() {
		if (webview2.canGoBack()) {
			webview2.goBack();
		}
		else {
			finish();
		}
	}
	public void _WebView (final boolean _js, final boolean _zoom, final boolean _download, final boolean _html, final boolean _cookies, final WebView _view) {
		_view.getSettings().setJavaScriptEnabled(_js); //Made by XenonDry
		CookieManager.getInstance().setAcceptCookie(_cookies);
		WebSettings webSettings = _view.getSettings(); 
		webSettings.setJavaScriptEnabled(_html); 
		webSettings.setJavaScriptCanOpenWindowsAutomatically(_html);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			webSettings.setAllowFileAccessFromFileURLs(_html); 
			webSettings.setAllowUniversalAccessFromFileURLs(_html);
		}
		if(_zoom == true){
			_view.getSettings().setBuiltInZoomControls(true);_view.getSettings().setDisplayZoomControls(false);
		}
		else if(_zoom == false){
			_view.getSettings().setBuiltInZoomControls(false);_view.getSettings().setDisplayZoomControls(true);
		}
		if(_download == true){
			
			_WebView_Download(webview2, "/Download");
		}
		else if(_download == false){
			showMessage("Downloads disabled!");
		}
	}
	
	
	public void _WebView_Download (final WebView _wview, final String _path) {
		if (FileUtil.isExistFile(_path)) {
			
		}
		else {
			FileUtil.makeDir(_path);
		}
		_wview.setDownloadListener(new DownloadListener() { public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) { DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url)); String cookies = CookieManager.getInstance().getCookie(url); request.addRequestHeader("cookie", cookies); request.addRequestHeader("User-Agent", userAgent); request.setDescription("Downloading file..."); request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype)); request.allowScanningByMediaScanner();
				
				
				
				 request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				 
				  java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/Download");if(!aatv.exists()){if (!aatv.mkdirs()){ Log.e("TravellerLog ::","Problem creating Image folder");}} request.setDestinationInExternalPublicDir(_path, URLUtil.guessFileName(url, contentDisposition, mimetype)); 
				
				DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE); manager.enqueue(request); showMessage("Downloading File...."); BroadcastReceiver onComplete = new BroadcastReceiver() { public void onReceive(Context ctxt, Intent intent) { showMessage("Download Complete!"); unregisterReceiver(this); }}; registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)); } }); 
	}
	
	
	public void _youtube_videos_fullscreen_webview () {
	}
	
	
	public class CustomWebClient extends WebChromeClient {
		private View mCustomView;
		private WebChromeClient.CustomViewCallback mCustomViewCallback;
		protected FrameLayout frame;
		
		// Initially mOriginalOrientation is set to Landscape
		private int mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		private int mOriginalSystemUiVisibility;
		
		// Constructor for CustomWebClient
		public CustomWebClient() {}
		
		public Bitmap getDefaultVideoPoster() {
			if (HomeActivity.this == null) {
				return null; }
			return BitmapFactory.decodeResource(HomeActivity.this.getApplicationContext().getResources(), 2130837573); }
		
		public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback viewCallback) {
			if (this.mCustomView != null) {
				onHideCustomView();
				return; }
			this.mCustomView = paramView;
			this.mOriginalSystemUiVisibility = HomeActivity.this.getWindow().getDecorView().getSystemUiVisibility();
			// When CustomView is shown screen orientation changes to mOriginalOrientation (Landscape).
			HomeActivity.this.setRequestedOrientation(this.mOriginalOrientation);
			// After that mOriginalOrientation is set to portrait.
			this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
			this.mCustomViewCallback = viewCallback; ((FrameLayout)HomeActivity.this.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1)); HomeActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
		}
		
		public void onHideCustomView() {
			((FrameLayout)HomeActivity.this.getWindow().getDecorView()).removeView(this.mCustomView);
			this.mCustomView = null;
			HomeActivity.this.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
			// When CustomView is hidden, screen orientation is set to mOriginalOrientation (portrait).
			HomeActivity.this.setRequestedOrientation(this.mOriginalOrientation);
			// After that mOriginalOrientation is set to landscape.
			this.mOriginalOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE; this.mCustomViewCallback.onCustomViewHidden();
			this.mCustomViewCallback = null;
		}
	}
	
	{
	}
	
	
	public void _youtube_fullscreen_webview_2 (final WebView _view) {
		_view.setWebChromeClient(new CustomWebClient());
		
	}
	

	private void setWindowFlag(final int bits, boolean on) {
		    Window win = getWindow();
		    WindowManager.LayoutParams winParams = win.getAttributes();
		    if (on) {
			        winParams.flags |= bits;
			    } else {
			        winParams.flags &= ~bits;
			    }
		    win.setAttributes(winParams);
	}
	{
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	

}
