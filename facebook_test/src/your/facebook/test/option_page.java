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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class option_page extends Activity {
	
	Facebook facebook = new Facebook("218394161563303");
	ImageView user_picture;
	String uname, facebookId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final Bundle bundle = this.getIntent().getExtras();
        String jsonUser = bundle.getString("param1");
        JSONObject obj=new JSONObject();

		try {
			obj = Util.parseJson(jsonUser);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FacebookError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	facebookId = obj.optString("id");
    	uname = obj.optString("name");
        
    	setContentView(R.layout.main);
        
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
       Button my_po = (Button) findViewById(R.id.myPoll);
       Button friend_po = (Button) findViewById(R.id.friendPoll);
       Button logout = (Button) findViewById(R.id.logout);
       txt.setTypeface(font);
       
       bundle.putString("param1", jsonUser);
       cre_po.setOnClickListener(new View.OnClickListener() {
       	
           public void onClick(View v) {
               Intent i=new Intent(option_page.this,create_poll.class);
               i.putExtras(bundle);
               startActivity(i);
           }
       });
       friend_po.setOnClickListener(new View.OnClickListener() {
          	
           public void onClick(View v) {
               Intent i=new Intent(option_page.this,friend_poll.class);
               i.putExtras(bundle);
               startActivity(i);
           }
       });
       my_po.setOnClickListener(new View.OnClickListener() {
          	
           public void onClick(View v) {
               Intent i=new Intent(option_page.this,my_poll.class);
               i.putExtras(bundle);
               startActivity(i);
               Log.i("my","fo");
           }
       });
       logout.setOnClickListener(new View.OnClickListener() {

       public void onClick(View v) {
               try {
					facebook.logout(option_page.this);
					Toast.makeText(option_page.this, "You have logout", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(option_page.this,Facebook_testActivity.class);
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
    }
}