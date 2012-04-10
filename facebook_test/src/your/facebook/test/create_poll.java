package your.facebook.test;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.FacebookError;
import com.facebook.android.Util;

public class create_poll extends Activity {

	private int mYear;
	private int mMonth;
	private int mDay;
	private EditText date_print,pd,pt,option_count;
	private Spinner cat, att;
	String str,jsonUser;
	static final int DATE_DIALOG_ID = 0;
	boolean comments_status=false;
	JSONArray comArr;
	JSONObject obj,obj1,optList,optList1;
	String uname,facebookId;
	Button sub,create_p;
	RadioButton rb;
	CheckBox cbcomm;
	ArrayList<EditText> arrOpt;
	LinearLayout opt_lay;
	 	public void onCheckboxClicked(View v) {
	    // Perform action on clicks, depending on whether it's now checked
	    if (((CheckBox) v).isChecked()) {
	        comments_status=true;
	    } else {
	    	comments_status=false;
	    	Log.v("my",String.valueOf(comments_status));
	    }
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                	 mYear = year;
                     mMonth = monthOfYear;
                     mDay = dayOfMonth;
                	updateDisplay();
                }
            };
     private void updateDisplay() {
                date_print.setText(
                    new StringBuilder()
                            // Month is 0 based so add 1
                    .append(mDay).append("/")        
                    .append(mMonth + 1).append("/")       
                     .append(mYear));
            }
     @Override
     protected Dialog onCreateDialog(int id) {
         switch (id) {
         case DATE_DIALOG_ID:
             return new DatePickerDialog(this,
                         mDateSetListener,
                         mYear, mMonth, mDay);
         }
         return null;
     }
     public void onRadioButtonClicked(View v) {
    	    // Perform action on clicks
    	    rb = (RadioButton) v;
    	    
    	}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.create_display);
        Bundle bundle = this.getIntent().getExtras();
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
    	
    	sub = (Button) findViewById(R.id.submit);
    	pt = (EditText)findViewById(R.id.poll_Topic);
    	pd = (EditText) findViewById(R.id.poll_des);
    	att = (Spinner) findViewById(R.id.spinner1);
    	cat = (Spinner) findViewById(R.id.spinner2);
    	option_count = (EditText) findViewById(R.id.options_cnt);
                
        str="list";
       

        Log.v("my",cat.toString());
        
         	date_print=(EditText) findViewById(R.id.dat);
         	ImageButton ca=(ImageButton) findViewById(R.id.calen);
         	ca.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showDialog(DATE_DIALOG_ID);
                }
            });

            // get the current date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            // display the current date (this method is below)
           updateDisplay();
            
            
           
         	
            sub.setOnClickListener(new View.OnClickListener() {
        	
    		public void onClick(View v)  {
            	
				
					
    		
            	    // Add your data   
            	
            	obj=new JSONObject();
            	 cat.setOnItemSelectedListener(new OnItemSelectedListener()
                 {
                     
                     public void onItemSelected(AdapterView<?> parent, View vw,
                             int pos, long id) {
                     	  
                     	  switch(cat.getSelectedItemPosition())
                           {
                           case 0:{str="like-dislike";break;}
                           case 1:{str="list";break;}
                           case 2:{str="stars";break;}
                           default : {str="list";break;}
                           }
                           
                       }
                     public void onNothingSelected(AdapterView<?> arg0) {
                         // Do nothing..
                     }
                 });
            	 att.setOnItemSelectedListener(new OnItemSelectedListener()
                 {
                     
                     public void onItemSelected(AdapterView<?> parent, View vw,
                             int pos, long id) {
                     	 String attri = att.getSelectedItem().toString();
                       }

                     
                     public void onNothingSelected(AdapterView<?> arg0) {
                         // Do nothing..
                     }
                 });
            	                 
            	 setContentView(R.layout.option_filling);
            	 Log.v("my","sdkfjskdhf"); 
            	 opt_lay=(LinearLayout) create_poll.this.findViewById(R.id.option_lay);
            	 
            	 arrOpt = new ArrayList<EditText>();
            	 Log.v("my","sdkfjskdasdfhf"); 
            	 LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            	    for (int i=0;i<Integer.parseInt(option_count.getText().toString());i++)
            	    {
            	    	arrOpt.add(new EditText(create_poll.this));
            	    	  
            	    	arrOpt.get(i).setLayoutParams(lparams);
            	    	  
            	    	arrOpt.get(i).setHint("Enter you option "+(i+1)+" here");
            	    	  
            	    	create_poll.this.opt_lay.addView(arrOpt.get(i));
            	    	  
            	    }
            	    Log.v("my","fuclk offff"); 
            	 create_p=new Button(create_poll.this);
            	 create_p.setLayoutParams(lparams);
            	 create_p.setText("Submit the poll");
            	 create_poll.this.opt_lay.addView(create_p);
            	 
            	 create_p.setOnClickListener(new View.OnClickListener() {
                  	
            		 public void onClick(View v)  {
            				HttpClient httpclient = new DefaultHttpClient();   
                        	HttpPost httppost = new HttpPost("http://10.100.70.53:3000/api/poll"); 
                        	//HttpPost httppost = new HttpPost("http://10.100.97.179:3000/api/poll"); 
                        	 httppost.addHeader("Content-type","application/json");
                        	 httppost.addHeader("Accept","application/json");			 
            			 optList=new JSONObject();
            			 comArr=new JSONArray();
            			 for (int i=0;i<Integer.parseInt(option_count.getText().toString());i++)
                 	    {
            				 try {
            					 optList=new JSONObject();
								optList.put("content", arrOpt.get(i).getText().toString());
								optList.put("who_likes","");
								optList.put("who_dislikes","");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
            				 comArr.put(optList);
                 	    }
            			 str="like-dislike";
            			 try {
                 		
             			obj.put("options_type", rb.getText().toString().toLowerCase());		
             			obj.put("description", pd.getText().toString());
             			obj.put("des_content_type", "text");
             			obj.put("title", pt.getText().toString());
             			obj.put("comments_enabled",comments_status );
             			obj.put("poll_method",str);
             			obj.put("options_list",comArr);
             			
             		} catch (JSONException e1) {
             			 //TODO Auto-generated catch block
             			Log.v("my","error");
             			e1.printStackTrace();
             		}
               
              
                obj1=new JSONObject();
                try {
             		
         			obj1.put("who", "Priyank");		
         			obj1.put("visibility", "public");
         			obj1.put("poll", obj);
         			
         		} catch (JSONException e1) {
         			 //TODO Auto-generated catch block
         			Log.v("my","error");
         			e1.printStackTrace();
         		}
              
                	
                Log.v("my",facebookId);
                
                StringEntity se=null;
                try {
                	se = new StringEntity(obj1.toString());
				} catch (UnsupportedEncodingException e1) {
				 //TODO Auto-generated catch block
					e1.printStackTrace();
				}  
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));		
                
			
           Log.v("my","rched hereeeeee");
            	    
            	    try{
            	    	se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json")); 
            	    	httppost.setEntity(se);
            	          
            	            httppost.setHeader("Accept", "application/json");
            	            httppost.setHeader("Content-type", "application/json");
            	            Log.d("myapp", "works till here. 2");
            	            try {
            	              
            	            	HttpResponse response= httpclient.execute(httppost);;
            	            
            	           
            	               
            	                Log.d("myapp", "works till here. 3");
            	                
            	            } catch (ClientProtocolException e) {
            	                e.printStackTrace();
            	            } catch (IOException e) {
            	                e.printStackTrace();
            	            }
            	        }catch (Exception e) {
            	            e.printStackTrace();
            	        }
            	
            	    
            	
            	Log.v("my","rched  4"); 
    			Toast.makeText(create_poll.this, "Poll submitted", Toast.LENGTH_LONG).show();
    			Bundle bundle = new Bundle();
    			bundle.putString("status", "OK");
    			Intent mIntent = new Intent();
    			mIntent.putExtras(bundle);
    			setResult(RESULT_OK, mIntent);
    			finish();
            		 }
            	 });
            }
            		 

			
            
        });
	}   
	}  
	  
   
    
    
    
    
   
    
