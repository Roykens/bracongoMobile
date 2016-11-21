package com.royken.bracongo.mobile;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.royken.bracongo.mobile.adapter.ImageAdapter;
import com.royken.bracongo.mobile.dao.PointDvao;
import com.royken.bracongo.mobile.entities.projection.ReponseProjection;
import com.royken.bracongo.mobile.util.AndroidNetworkUtility;
import com.royken.bracongo.mobile.util.ReponseService;
import com.royken.bracongo.mobile.util.RetrofitBuiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by royken on 01/05/16.
 */
public class CommentFragment extends Fragment implements View.OnClickListener {

    public static final String PREFS_NAME = "com.royken.MyPrefsFile";
    Button captureBtn = null;
    final int CAMERA_CAPTURE = 1;

    private Uri picUri;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ReponseProjection reponseProjection;

    private EditText commentTx;

    private Button envoyer;

    private OnFragmentInteractionListener mListener;

    private PointDvao pointDvao;
    private List<String> myList;  // String list that contains file paths to images
    private GridView gridview;
    private String mCurrentPhotoPath;  // File path to the last image captured
    public static final String GridViewDemo_ImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/GridViewDemo/";


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentFragment newInstance() {
        CommentFragment fragment = new CommentFragment();

        return fragment;
    }

    public CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.comment_fragment, container, false);
        reponseProjection = (ReponseProjection) getActivity().getApplicationContext();
        pointDvao = new PointDvao(getActivity().getApplicationContext());
        // Initialize GridView
        gridview = (GridView) rootView.findViewById(R.id.gridView1);
       // gridview.setAdapter(new ImageAdapter(getActivity().getApplicationContext()));
      //  photo = (Button)rootView.findViewById(R.id.photo);
        commentTx = (EditText)rootView.findViewById(R.id.comment);
        envoyer = (Button)rootView.findViewById(R.id.envoyerBtn);
        envoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reponseProjection.setCommentaire(commentTx.getText().toString());
                AndroidNetworkUtility androidNetworkUtility = new AndroidNetworkUtility();
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                String url = settings.getString("com.royken.url", "");
                if (!androidNetworkUtility.isConnected(getActivity())) {
                    Toast.makeText(getActivity(), "Aucune connexion au serveur. Veuillez reéssayer plus tard", Toast.LENGTH_LONG).show();
                } else {
                    new BackgroundTask().execute();
                }
            }
        });
        FloatingActionButton myFab = (FloatingActionButton)  rootView.findViewById(R.id.fab);


        myList = null;
        myList = RetriveCapturedImagePath();
        if(myList!=null){
            gridview.setAdapter(new ImageListAdapter(getActivity(),myList));
        }
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePicture();
            }
        });
      /*  photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });*/
        return rootView;
    }

    @Override
    public void onClick(View arg0) {
// TODO Auto-generated method stub
        if (arg0.getId() == R.id.fab) {
            takePicture();
          /*  try {
//use standard intent to capture an image
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//we will handle the returned data in onActivityResult
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch(ActivityNotFoundException anfe){
//display an error message
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }*/
        }

    }/*


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
//user is returning from capturing an image using the camera
                    if (requestCode == CAMERA_CAPTURE) {
                        Bundle extras = data.getExtras();
                        Bitmap thePic = extras.getParcelable("data");
                        String imgcurTime = dateFormat.format(new Date());
                        File imageDirectory = new File(GridViewDemo_ImagePath);
                        imageDirectory.mkdirs();
                        String _path = GridViewDemo_ImagePath + imgcurTime + ".jpg";
                        try {
                            FileOutputStream out = new FileOutputStream(_path);
                            thePic.compress(Bitmap.CompressFormat.JPEG, 90, out);
                            out.close();
                        } catch (FileNotFoundException e) {
                            e.getMessage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        myList = null;
                        myList = RetriveCapturedImagePath();
                        if (myList != null) {
                            gridview.setAdapter(new ImageListAdapter(getActivity(), myList));
                        }
                    }
                }
        }
    }
*/
    private List<String> RetriveCapturedImagePath() {
        List<String> tFileList = new ArrayList<String>();
        File f = new File(GridViewDemo_ImagePath);
        if (f.exists()) {
            File[] files=f.listFiles();
            Arrays.sort(files);

            for(int i=0; i<files.length; i++){
                File file = files[i];
                if(file.isDirectory())
                    continue;
                tFileList.add(file.getPath());
            }
        }
        return tFileList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Envoi");

    }



    public class ImageListAdapter extends BaseAdapter
    {
        private Context context;
        private List<String> imgPic;
        public ImageListAdapter(Context c, List<String> thePic)
        {
            context = c;
            imgPic = thePic;
        }
        public int getCount() {
            if(imgPic != null)
                return imgPic.size();
            else
                return 0;
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ImageView imageView;
            BitmapFactory.Options bfOptions=new BitmapFactory.Options();
            bfOptions.inDither=false;                     //Disable Dithering mode
            bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
            bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
            bfOptions.inTempStorage=new byte[32 * 1024];
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setPadding(0, 0, 0, 0);
            } else {
                imageView = (ImageView) convertView;
            }
            FileInputStream fs = null;
            Bitmap bm;
            try {
                fs = new FileInputStream(new File(imgPic.get(position).toString()));

                if(fs!=null) {
                    bm= BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
                    imageView.setImageBitmap(bm);
                    imageView.setId(position);
                    imageView.setLayoutParams(new GridView.LayoutParams(200, 160));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(fs!=null) {
                    try {
                        fs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return imageView;
        }
    }


    private void takePicture() {
        Log.i("BONNNNNNN","bonnnn");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE );
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }catch (IOException ex){
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore. EXTRA_OUTPUT,
                       Uri. fromFile(photoFile));
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format( new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" ;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment. DIRECTORY_PICTURES);
        File image = File. createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            // Save Image To Gallery
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
            File f = new File(mCurrentPhotoPath );
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);

            // Add Image Path To List
            myList.add( mCurrentPhotoPath);

            // Refresh Gridview Image Thumbnails
            gridview.invalidateViews();
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    private class BackgroundTask extends AsyncTask<String, Void,
            Void> {


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... urls) {

            Log.i("Bacground","background test");
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String url = settings.getString("com.royken.url","");
            Retrofit retrofit = RetrofitBuiler.getRetrofit(url+"/");
            ReponseService service = retrofit.create(ReponseService.class);
            Call<ReponseProjection> call= service.envoyerReponse(reponseProjection);
            call.enqueue(new Callback<ReponseProjection>() {
                @Override
                public void onResponse(Call<ReponseProjection> call, Response<ReponseProjection> response) {
                    // Log.i("Retrofit Logging", response.body().toString());
                    pointDvao.deletePointDeVente(reponseProjection.getIdPdv());
                    reponseProjection.init();

                    Toast.makeText(getActivity(),"Donnée envoyée avec succès",Toast.LENGTH_LONG).show();
                    Fragment fragment = PlanningFragment.newInstance("","");
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainFrame, fragment);
                    ft.addToBackStack(null);

                    ft.commit();

                }

                @Override
                public void onFailure(Call<ReponseProjection> call, Throwable t) {
                    Log.e("Retrofit Logging2", t.toString());
                    Toast.makeText(getActivity(),"Echec d'envoi",Toast.LENGTH_LONG).show();

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {


        }
    }
}
