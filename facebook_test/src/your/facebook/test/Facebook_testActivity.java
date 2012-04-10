package your.facebook.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class Facebook_testActivity extends Activity {
	String jsonUser="";
    Facebook facebook = new Facebook("218394161563303");
    Bundle bundle;
    boolean fb=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        Typeface font = Typeface.createFromAsset(getAssets(), "King_Cool_KC.ttf");
        TextView txt1 = (TextView) findViewById(R.id.Title);
        //Button cre_po = (Button) findViewById(R.id.createPoll);
       
        ImageButton login = (ImageButton) findViewById(R.id.login);
        txt1.setTypeface(font);
       
        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            	
            	 final Bundle bundle = new Bundle();
                 bundle.putString("param1", jsonUser);
                 Intent i=new Intent(Facebook_testActivity.this,option_page.class);
                 i.putExtras(bundle);
                 startActivity(i);
                
                if(fb)
                {
            	facebook.authorize(Facebook_testActivity.this,  new DialogListener() 
           		{
               
                public void onComplete(Bundle values)   {
                	
					try {
						jsonUser = facebook.request("me");
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	JSONObject obj=null;
					try {
						obj = Util.parseJson(jsonUser);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FacebookError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	String facebookId = obj.optString("id");
                	String uname = obj.optString("name");
                	 final Bundle bundle = new Bundle();
                     bundle.putString("param1", jsonUser);
                     Intent i=new Intent(Facebook_testActivity.this,option_page.class);
                     i.putExtras(bundle);
                     startActivity(i);
                	
                	/*
                	setContentView(R.layout.main);
                	ImageView user_picture;
                	 user_picture=(ImageView)findViewById(R.id.p_image);
                	 URL img_value = null;
                	 try {
						img_value = new URL("http://graph.facebook.com/"+facebookId+"/picture?type=large");
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	 Bitmap mIcon1=null;
					try {
						mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	 user_picture.setImageBitmap(mIcon1);
                	Typeface font = Typeface.createFromAsset(getAssets(), "King_Cool_KC.ttf");
                    TextView txt = (TextView) findViewById(R.id.Title);
                    TextView user_text = (TextView) findViewById(R.id.user_name);
                    user_text.setText(uname.replace(' ', '\n'));
                    
                    Button cre_po = (Button) findViewById(R.id.createPoll);
                    Button logout = (Button) findViewById(R.id.logout);
                    txt.setTypeface(font);
                   bundle = new Bundle();
                    bundle.putString("param1", jsonUser);
                    cre_po.setOnClickListener(new View.OnClickListener() {
                    	
                        public void onClick(View v) {
                            Intent i=new Intent(Facebook_testActivity.this,create_poll.class);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                    logout.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            try {
            					facebook.logout(Facebook_testActivity.this);
            					Toast.makeText(Facebook_testActivity.this, "You have logout", Toast.LENGTH_LONG).show();
            					Intent intent = getIntent();
            					finish();
            					startActivity(intent);
            					
            				} catch (MalformedURLException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				} catch (IOException e) {
            					// TODO Auto-generated catch block
            					e.printStackTrace();
            				}
                        }
                    });
              */      
                    
                }

              
               public void onFacebookError(FacebookError error) 
               {
            	   finish();
               }

               
                public void onError(DialogError e) 
                {
                	finish();
                }

               
                public void onCancel() 
                {
                	Toast.makeText(Facebook_testActivity.this, "Cancelled login", Toast.LENGTH_LONG).show();
                	finish();
                }
            });
                
            }}
        });
        
        

       
    }
    


    
        
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    
}
    
}