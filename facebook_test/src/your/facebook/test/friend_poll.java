package your.facebook.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;


public class friend_poll extends Activity{
	boolean flag=true;
	 public void onRadioButtonClicked1(View v) {
 	    // Perform action on clicks
 	    rb=(RadioButton)v;
 	    
 	    radio[0]=rb.getText().toString().toLowerCase();
 	}
	 public void onRadioButtonClicked2(View v) {
	 	    // Perform action on clicks
		 rb=(RadioButton)v;
		 radio[1]=rb.getText().toString().toLowerCase();
	 	    
	 	}
	 public void onRadioButtonClicked3(View v) {
	 	    // Perform action on clicks
		 rb=(RadioButton)v;
		 radio[2]=rb.getText().toString().toLowerCase();
	 	    
	 	}
private static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	public void changeDisplay(View view) {
    flag=true;
	}
	HttpPost httppost;
    TextView title_h,des_h,test;
	String jsonUser,facebookId,uname,result,typ;
	JSONObject obj,obj1,upd;
	String radio[]=new String[3];
	RadioButton rb;
	JSONArray mJsonArray,option_out;
	ScrollView scrl;
	RelativeLayout llay,llay1;
	ImageView user_picture;
	URL img_value;
	Button nextp,vote_sub,back;
	Bundle bundle;
	Bitmap mIcon1;
	 int ctr=0;
	private void updateDisplay(int i)
	{
      	   Log.v("loop","roaming in loop "+i);
      	   
			try {
				llay= new RelativeLayout(this);
				obj=new JSONObject();
				obj = mJsonArray.getJSONObject(i);
				obj1= obj.optJSONObject("poll");
			        title_h = (TextView) findViewById(R.id.topic_d_ans);
			        title_h.setText(obj1.getString("title"));
			        des_h = (TextView) findViewById(R.id.desc_d_ans);
			        des_h.setText(obj1.getString("description"));
			        option_out=obj1.getJSONArray("options_list");
			        typ=obj1.getString("options_type");
			        test=(TextView)findViewById(R.id.user_name);
			        test.setText(obj.getString("who"));
			        Log.v("my","not out of catch");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 Log.v("length","len = "+option_out.length());
			
				
	        
	        if (!typ.contains("te"))
	        {
	        	llay=(RelativeLayout) findViewById(R.id.image_display);
	        	Log.v("length","leninside if"+typ);
	        	llay.setVisibility(View.VISIBLE);
	        }
	        Log.v("length","len"+typ);
	        int t=0;
	        ImageButton ib=(ImageButton) findViewById(R.id.image3);
	        llay=new RelativeLayout(this);
	        Log.v("length","len"+option_out.length());
	        switch(option_out.length())
	        {
	        case 2:{t=R.id.displayPoll_2;llay=(RelativeLayout) findViewById(t);llay.setVisibility(View.VISIBLE);ib.setVisibility(View.INVISIBLE); Log.v("my","rched1"+2);break;}
	        
	        case 3:{t=R.id.displayPoll_3;llay=(RelativeLayout) findViewById(t);llay.setVisibility(View.VISIBLE);ib.setVisibility(View.VISIBLE); Log.v("my","rched1"+3);break;}
	        
	        default:{Toast.makeText(friend_poll.this, "There is Error", Toast.LENGTH_LONG).show();break;}
	        }
	        Log.v("my","rched1after switch");
	        for(int j=0;j<option_out.length();j++)
	        {
	        	
	        		
	        	test=(TextView) findViewById(t+j+1);
	        	 Log.v("my","rched1");
	        	 if(!typ.contains("te"))
	        	 {
	        	 img_value = null;
          	 try {
					img_value = new URL("http://10.100.70.23/create_poll/uploads/"+option_out.getJSONObject(j).getString("content"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
          	mIcon1=null;
				try {
					mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
          	 user_picture.setImageBitmap(mIcon1);
	        	 }
	        	 Log.v("my","rched1before setting");
	        	try {
					test.setText(option_out.getJSONObject(j).getString("content"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	
	        } 
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("my","rched");
        setContentView(R.layout.vote_dis);
        bundle = this.getIntent().getExtras();
        jsonUser = bundle.getString("param1");
        
         
        obj=new JSONObject();
        try {
			obj = Util.parseJson(jsonUser);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FacebookError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        facebookId = obj.optString("id");
    	uname = obj.optString("name");
    	user_picture=(ImageView)findViewById(R.id.p_image);
   	 	img_value = null;
   	 	try {
			img_value = new URL("http://graph.facebook.com/"+facebookId+"/picture?type=large");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   	 	mIcon1=null;
		try {
			mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   	 	user_picture.setImageBitmap(mIcon1);
   	 
    	
   	 		HttpClient httpclient = new DefaultHttpClient();

    	    // Prepare a request object
    	    HttpGet httpget = new HttpGet("http://10.100.70.53:3000/api/poll/");//get_poll_by_id/4f81b1be5ff860af19000011"); 
    	    // Execute the request
    	    HttpResponse response;
    	    try {
    	        response = httpclient.execute(httpget);
    	        HttpEntity entity = response.getEntity();
    	        
    	        if (entity != null) {

    	            // A Simple JSON Response Read
    	            InputStream instream = entity.getContent();
    	            result= convertStreamToString(instream);
    	           
    	            // now you have the string representation of the HTML request
    	           
    	            instream.close();
    	            Log.v("the result",result);
    	        }

    	        Log.i("my","rched");
    	    } catch (Exception e) {}
    	    Log.v("my","rched");
    	    
    	    obj=new JSONObject();
    	
    	    //ArrayList<LinearLayout> llay1= new  ArrayList<LinearLayout>(); 
    	    try {
    	   	 Log.v("my","rched1");
				mJsonArray = new JSONArray(result);
				 Log.v("my","rched1");
				option_out =new JSONArray();
				 Log.v("my","rched1");
				obj= new JSONObject();
				 Log.v("my","rched1");
				
			updateDisplay(ctr);
			nextp=(Button) findViewById(R.id.nextPoll);
			nextp.setOnClickListener(new View.OnClickListener() {
            	
                public void onClick(View v) {
                    ctr++;
                    updateDisplay(ctr);
              
                }
            });
			back=(Button)findViewById(R.id.back_Option);
			back.setOnClickListener(new View.OnClickListener() {
            	
                public void onClick(View v) {
                	 Intent i=new Intent(friend_poll.this,option_page.class);
                     i.putExtras(bundle);
                     startActivity(i);
                    
              
                }
            });

			Log.v("my","outofupdate");
			upd=new JSONObject();
			httppost = new HttpPost("http://10.100.70.53:3000/api/poll/update/"+obj.getString("_id")); 
			httppost.addHeader("Content-type","application/json");
       	 	httppost.addHeader("Accept","application/json");
       	 	StringEntity se;
       	 	vote_sub=(Button)findViewById(R.id.vote);
			vote_sub.setOnClickListener(new View.OnClickListener() {
            	
                public void onClick(View v) {
                	for(int i=0;i<option_out.length();i++)
                	{		
                		upd=new JSONObject();
                		try {
							upd.put("content",i+1);
							upd.put("choice", radio[i]);
							upd.put("id",facebookId);
							Log.v("my",upd.toString());
							 StringEntity se=null;
				                try {
				                	se = new StringEntity(upd.toString());
								} catch (UnsupportedEncodingException e1) {
								 //TODO Auto-generated catch block
									e1.printStackTrace();
								}  
				                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));	
				                httppost.setEntity(se);
				                try {
				                		HttpClient httpclient = new DefaultHttpClient();
				                		httppost.setHeader("Accept", "application/json");
			            	            httppost.setHeader("Content-type", "application/json");
			            	            HttpResponse response= httpclient.execute(httppost);
			            	            Log.d("myapp", "works till here. 3");
	            	                
	            	            } catch (ClientProtocolException e) {
	            	                e.printStackTrace();
	            	            } catch (IOException e) {
	            	                e.printStackTrace();
	            	            }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	}
                	Toast.makeText(friend_poll.this, "Vote submitted", Toast.LENGTH_LONG).show();
        			bundle = new Bundle();
        			bundle.putString("status", "OK");
        			Intent mIntent = new Intent();
        			mIntent.putExtras(bundle);
        			setResult(RESULT_OK, mIntent);
        			finish();
              
                }
            });
				    	        
	    	        Log.v("my","rasasched");
	    		    	        
	    	     
			} catch (Exception e) {
				 //TODO Auto-generated catch block
				//e.printStackTrace();
			}
	}
}