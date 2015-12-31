package com.compta.firstak.notedefrais;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ProxySelector;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import leadtools.ILeadStream;
import leadtools.LeadRect;
import leadtools.LeadStreamFactory;
import leadtools.RasterColor;
import leadtools.RasterImage;
import leadtools.codecs.CodecsLoadAsyncCompletedEvent;
import leadtools.codecs.CodecsLoadAsyncCompletedListener;
import leadtools.codecs.RasterCodecs;
import leadtools.controls.ImageViewerPanZoomInteractiveMode;
import leadtools.controls.ImageViewerSizeMode;
import leadtools.controls.RasterImageViewer;
import leadtools.demos.DeviceUtils;
import leadtools.demos.Messager;
import leadtools.demos.OpenFileDialog;
import leadtools.demos.Progress;
import leadtools.demos.Support;
import leadtools.demos.Utils;
import leadtools.forms.ocr.OcrAutoPreprocessPageCommand;
import leadtools.forms.ocr.OcrDocument;
import leadtools.forms.ocr.OcrEngine;
import leadtools.forms.ocr.OcrEngineManager;
import leadtools.forms.ocr.OcrEngineType;
import leadtools.forms.ocr.OcrPage;
import leadtools.forms.ocr.OcrProgressData;
import leadtools.forms.ocr.OcrProgressEvent;
import leadtools.forms.ocr.OcrProgressListener;
import leadtools.forms.ocr.OcrProgressOperation;
import leadtools.forms.ocr.OcrProgressStatus;
import leadtools.forms.ocr.OcrSettingManager;
import leadtools.forms.ocr.OcrZone;
import leadtools.imageprocessing.RotateCommand;
import leadtools.imageprocessing.RotateCommandFlags;
import leadtools.imageprocessing.color.InvertCommand;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton Choose_Picture;
    private FloatingActionButton Take_Picture;
    private FloatingActionButton File;
    FloatingActionMenu menuLabelsRight;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();




    private static final int IMAGE_GALLERY = 0x0001;
    private static final int IMAGE_CAPTURE = 0x0002;

    private static final String OCR_TEMP_DIRECTORY = Environment.getExternalStorageDirectory() + "/NoteDeFrais/app/";
    private static final String OCR_TEMP_DIRECTORYImage = Environment.getExternalStorageDirectory() + "/NoteDeFrais/signImage/";
    private static final String OCR_RUNTIME_DIRECTORY = OCR_TEMP_DIRECTORY + "OCRRuntime/";
    private static String url_Ajout_SARL="http://192.168.43.247/comptable/Client/sarl.php";
    private Uri mImageCaptureUri;


    private ColorMatrixColorFilter mScaleColorFilter;
    public  String OcrResult;
    public ArrayList<String> ImageFile = new ArrayList<String>();

    public  String DesignationResultat;

    private boolean mAutoInvertEnabled;
    private boolean mIsWorking;
    private RasterImageViewer mImageViewer;
    private ProgressDialog mProgressDlg;
    private OcrEngine mOcrEngine;
    Uri source1;
    Bitmap processedBitmap;
    public  Bitmap bitmap;
    public  StringBuilder text;
    public static ArrayList<String> Dictionnaire;
    public static String Date;
    public  byte[] byteArray;
    ChoixPhotoResult ch;
    String[] OcrWordIntent;
    ILeadStream stream;

    Boolean TestTel;
    Boolean TestNfacture;
    Boolean TestMatriculeFiscal;
    Boolean TestMF;
    Boolean TestTva;
    Boolean TestTottal;
    Boolean TestHTVA;
    Boolean TestFax;
    Boolean TestEmail;
    Boolean TestSie;
    Boolean TestDate;

    static {

        System.loadLibrary("opencv_java");

        if (!OpenCVLoader.initDebug())
        {
            Log.i("openCV", "non");
        }
        else
        {
            Log.i("openCV", "oui");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        // Set License
        Support.setLicense(this);

        if (Support.isKernelExpired()) {
            Messager.showKernelExpiredMessage(this, new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });

            return;
        }


        //---------------------------------OpenCV------------------------
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //---------------------------------------------------------------


        ColorMatrix cm = new ColorMatrix();
        cm.setScale(.75f, .75f, .75f, .75f);
        mScaleColorFilter = new ColorMatrixColorFilter(cm);

        mAutoInvertEnabled = true;
        mIsWorking = false;

        mImageViewer = (RasterImageViewer) findViewById(R.id.imageviewer);
        mImageViewer.setTouchInteractiveMode(new ImageViewerPanZoomInteractiveMode());
        mImageViewer.setSizeMode(ImageViewerSizeMode.FIT);
        mImageViewer.setUseDpi(true);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mImageViewer.setScreenDpiX(metrics.densityDpi);
        mImageViewer.setScreenDpiY(metrics.densityDpi);

        copyLanguageFiles();
        init();



        //------------------------------------Menu--------------------------

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         menuLabelsRight = (FloatingActionMenu) findViewById(R.id.menu_labels_right);

        final FloatingActionButton programFab1 = new FloatingActionButton(this);
        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab1.setLabelText("Programmatically added button");
        programFab1.setImageResource(R.drawable.ic_edit);
        programFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, programFab1.getLabelText(), Toast.LENGTH_SHORT).show();
            }
        });


        ContextThemeWrapper context = new ContextThemeWrapper(this, R.style.MenuButtonsStyle);
        FloatingActionButton programFab2 = new FloatingActionButton(context);
        programFab2.setLabelText("Programmatically added button");
        programFab2.setImageResource(R.drawable.ic_edit);

        menus.add(menuLabelsRight);


        menuLabelsRight.hideMenuButton(false);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }
        Choose_Picture = (FloatingActionButton) findViewById(R.id.ChoosePicture);
        Take_Picture = (FloatingActionButton) findViewById(R.id.Takepicture);
        File = (FloatingActionButton) findViewById(R.id.File);


        //------------------------------------------------------------------


    }

    private boolean copyLanguageFiles() {
        try {
            Utils.createDirectory(OCR_RUNTIME_DIRECTORY);
            String assetsDirectoryName = "ocr_runtime";
            String[] languagesFilesNames = getAssets().list(assetsDirectoryName);
            if (languagesFilesNames.length == 0)
                return false;

            for (String languageFile : languagesFilesNames) {
                String name = String.format("%s%s", OCR_RUNTIME_DIRECTORY, languageFile);
                File file = new File(name);
                if (!file.exists()) {
                    // copy the language file
                    InputStream is = getAssets().open(assetsDirectoryName + "/" + languageFile);
                    OutputStream os = new FileOutputStream(name);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = is.read(buffer)) != -1) {
                        os.write(buffer, 0, read);
                    }
                    os.close();
                    is.close();
                }
            }

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void init() {
        try {
            //-----------------------------------------------------------------------------------

            RasterCodecs codecsForOCR = new RasterCodecs(Utils.getSharedLibsPath(this));
            codecsForOCR.getOptions().getLoad().setXResolution(300);
            codecsForOCR.getOptions().getLoad().setYResolution(300);

            mOcrEngine = OcrEngineManager.createEngine(OcrEngineType.Advantage);
            mOcrEngine.startup(codecsForOCR, "", OCR_RUNTIME_DIRECTORY, Utils.getSharedLibsPath(this));
            OcrSettingManager settingsManager = mOcrEngine.getSettingManager();
            settingsManager.setBooleanValue("Recognition.Preprocess.MobileImagePreprocess", true);
            //----------------------------------------------------------------------------------
            settingsManager.setBooleanValue("Recognition.ShareOriginalImage", true);
            settingsManager.setBooleanValue("Recognition.ModifyProcessingImage", true);
        } catch (Exception ex) {
            Messager.showError(this, ex.getMessage(), "Error");
        }
    }

    private void resetInteractiveMode() {
        mImageViewer.setTouchInteractiveMode(new ImageViewerPanZoomInteractiveMode());
    }

    public void onViewAction(View v) {
        RasterImage image = mImageViewer.getImage();
        if (image == null) {
            Messager.showError(this, "No image loaded", null);
            return;
        }

        resetInteractiveMode();
        int id = v.getId();
    }

    public void onImageAction(View v) {
        if(mIsWorking)
            return;

        resetInteractiveMode();

        RasterImage image = mImageViewer.getImage();
        if(image == null) {
            Messager.showError(this, "No image loaded", null);
            return;
        }

        try {
            mIsWorking = true;
            int id = v.getId();
            if (id == R.id.btn_rotate_cw) {
                RotateCommand command = new RotateCommand(9000, RotateCommandFlags.RESIZE.getValue(), new RasterColor(0, 0, 0));
                command.run(image);
                onSelectArea();
            } else if (id == R.id.btn_rotate_ccw) {
                RotateCommand command = new RotateCommand(-9000, RotateCommandFlags.RESIZE.getValue(), new RasterColor(0, 0, 0));
                command.run(image);
                //onSelectArea();
            }

        } catch(Exception ex) {
            Messager.showError(this, ex.getMessage(), "Rotate Image");
        } finally {
            mIsWorking = false;
            onSelectArea();
        }
    }


    public void onSelectImage(View v) {
        int id = v.getId();

        if (id == R.id.ChoosePicture) {
            Intent gallery = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, IMAGE_GALLERY);
        } else if (id == R.id.Takepicture) {
            if (!DeviceUtils.isMediaMounted()) {
                Messager.showError(this, "The sdcard is not mounted", null);
                return;
            } else if (!DeviceUtils.hasCamera(this)) {
                Messager.showError(this, "The device doesn't have a camera", null);
                return;
            }

            Utils.createDirectory(OCR_TEMP_DIRECTORY);

            mImageCaptureUri = Utils.getExtFileUri("", ".jpg", OCR_TEMP_DIRECTORY);
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            camera.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            startActivityForResult(camera, IMAGE_CAPTURE);

        } else if (id == R.id.File) {
            if (!DeviceUtils.isMediaMounted()) {
                Messager.showError(this, "The sdcard is not mounted", null);
                return;
            }

            OpenFileDialog.OnFileSelectedListener onFileSelectedListener = new OpenFileDialog.OnFileSelectedListener() {
                @Override
                public void onFileSelected(String fileName) {
                    File file = new File(fileName);
                    if (file.exists())

                    stream = LeadStreamFactory.create(fileName);
                    loadImage(stream);
                }
            };

            OpenFileDialog openDlg = new OpenFileDialog(this, Utils.getSupportedImagesFormatFilter(), onFileSelectedListener);
            openDlg.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String imageFileName = null;
            switch (requestCode) {
                case IMAGE_GALLERY:
                    source1 = data.getData();
                    imageFileName = Utils.getGalleryPathName(this.getContentResolver(), data.getData());
                    ImageFile.add(imageFileName);
                    for (int i = 0; i < ImageFile.size(); i++) {
                        Toast.makeText(getApplicationContext(), ImageFile.get(i),
                                Toast.LENGTH_LONG).show();
                    }


                    break;

                case IMAGE_CAPTURE:
                        String uriPath = mImageCaptureUri.getPath();
                        if (uriPath != null) {
                            File file = new File(uriPath);
                            if (file.exists())
                                imageFileName = uriPath;
                            Opencv(imageFileName);
                        }
                    break;

                default:
                    break;
            }

            if (imageFileName != null) {
                //-------------------------------OpenCV----------------------------
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imageFileName, options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
                if(imageWidth<2322 &&imageHeight<4128 ) {
                    Opencv(imageFileName);
                   stream=LeadStreamFactory.create(byteArray);
                }
                else{
                   stream = LeadStreamFactory.create(imageFileName);
                }
                //------------------------------------------------------------------

                loadImage(stream);
            }
        }
    }


    private void loadImage(ILeadStream stream) {
        try {
            mProgressDlg = Progress.show(this, "Load Image", "Loading");
            RasterCodecs codecs = new RasterCodecs(Utils.getSharedLibsPath(this));
            codecs.addLoadAsyncCompletedListener(new CodecsLoadAsyncCompletedListener() {
                @Override
                public void onLoadAsyncCompleted(CodecsLoadAsyncCompletedEvent event) {
                    Progress.close(mProgressDlg);
                    if (event.getError() != null || event.getCancelled()) {
                        Messager.showError(MainActivity.this, event.getError().getMessage(), "Error loading file");
                    } else {
                        setImage(event.getImage());
                        updateImageInfo();
                        onSelectArea();
                        menuLabelsRight.close(true);
                    }
                }
            });

            // Load image
            codecs.loadAsync(stream, null);

        } catch (Exception ex) {
            Progress.close(mProgressDlg);
            Messager.showError(MainActivity.this, ex.getMessage(), "Error loading file");
        }

    }



    public void Opencv(String imageName){
        bitmap = BitmapFactory.decodeFile(imageName);
        Mat imageMat = new Mat();
        org.opencv.android.Utils.bitmapToMat(bitmap, imageMat);

        Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_BGR2GRAY);
        // 1) Apply gaussian blur to remove noise
        Imgproc.GaussianBlur(imageMat, imageMat, new Size(9,9), 0);
// 2) AdaptiveThreshold -> classify as either black or white
        Imgproc.adaptiveThreshold(imageMat, imageMat, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 2);

// 3) Invert the image -> so most of the image is black
        Core.bitwise_not(imageMat, imageMat);

// 4) Dilate -> fill the image using the MORPH_DILATE
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_DILATE, new Size(3,3), new Point(1,1));
        Imgproc.dilate(imageMat, imageMat, kernel);

        org.opencv.android.Utils.matToBitmap(imageMat, bitmap);
        mImageViewer.setImageBitmap(bitmap);
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream1);
        byteArray = stream1.toByteArray();
    }




    private void updateImageInfo() {
        RasterImage image = mImageViewer.getImage();
        if (image != null) {
            Toast.makeText(getApplicationContext(),
                    (String.format("(%1$s x %2$s)", image.getWidth(), image.getHeight())),
                    Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(getApplicationContext(),
                    "No Image Loaded",
                    Toast.LENGTH_LONG).show();


        }
    }



    //------------------------------------------------------------------------------------------------------
    private Bitmap ProcessingBitmap(){
        Bitmap bm1 = null;
        Bitmap newBitmap = null;

        try {
            bm1 = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(source1));

            Bitmap.Config config = bm1.getConfig();
            if(config == null){
                config = Bitmap.Config.ARGB_8888;
            }

            newBitmap = Bitmap.createBitmap(bm1.getWidth(), bm1.getHeight(), config);
            Canvas newCanvas = new Canvas(newBitmap);

            newCanvas.drawBitmap(bm1, 0, 0, null);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date= sdf.format(new Date());
            String captionString = "1/"+Date+"/Paye/Firstak";
            if(captionString != null){

                Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
                paintText.setColor(Color.RED);
                paintText.setTextSize(250);
                paintText.setStyle(Paint.Style.FILL);
                paintText.setShadowLayer(10f, 10f, 10f, Color.BLACK);

                Rect rectText = new Rect();
                paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

                newCanvas.drawText(captionString,
                        0, rectText.height(), paintText);

                Toast.makeText(getApplicationContext(),
                        "drawText: " + captionString,
                        Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(getApplicationContext(),
                        "caption empty!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newBitmap;
    }
    private void ProscessSaveImg(){
        if (source1 != null) {
            processedBitmap = ProcessingBitmap();
            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                processedBitmap.compress(Bitmap.CompressFormat.JPEG, 60, bytes);
                                                     // File f = new File(OCR_TEMP_DIRECTORYImage + File.separator + "test.jpg");
                File f = new File(OCR_TEMP_DIRECTORYImage + "test.jpg");
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    //------------------------------------------------------------------------------------
    private void setImage(RasterImage image) {
        try {
            mImageViewer.setImage(image);

        } catch (Exception ex) {
            Messager.showError(this, ex.getMessage(), "");
        } finally {
            resetInteractiveMode();
        }
    }

    public void onRecognizePage(View v) {
        if (mIsWorking)
            return;

        RasterImage image = mImageViewer.getImage();
        if (image == null) {
            Messager.showError(this, "No image loaded", null);
            return;
        }
        ProscessSaveImg();
        try {
            LeadRect rc = null;
            if (mImageViewer.getTouchInteractiveMode() instanceof SelectAreaInteractiveMode) {
                rc = ((SelectAreaInteractiveMode) mImageViewer.getTouchInteractiveMode()).getSelectedImageRectangle();
                // reset the interactive mode to Pan Zoom
               resetInteractiveMode();

                new RecognizeTextTask().execute(new RecognizeTextTaskParams(image.clone(), null));
                new RecognizeTextTask().execute(new RecognizeTextTaskParams(image.clone(), rc));


            } else {
                Toast.makeText(getApplicationContext(), "use the zone Designation",
                        Toast.LENGTH_LONG).show();
            }


        } catch (Exception ex) {
            Messager.showError(this, ex.getMessage(), "Error");
        }
    }
    public void onSelectArea() {
        if(mIsWorking)
            return;

        RasterImage image = mImageViewer.getImage();
        if(image == null) {
            Messager.showError(this, "No image loaded", null);
            return;
        }

        if(mImageViewer.getTouchInteractiveMode() instanceof SelectAreaInteractiveMode) {
            // reset the interactive mode to Pan Zoom
            resetInteractiveMode();
        } else {
            mImageViewer.setTouchInteractiveMode(new SelectAreaInteractiveMode());
        }
    }


    //--------------------------------------------------------------------------------------------------
    private String getOperationFriendlyName(OcrProgressOperation operation) {
        String friendlyName = "";
        switch (operation) {
            case LOAD_IMAGE:
                friendlyName = "Load Image";
                break;
            case SAVE_IMAGE:
                friendlyName = "Save Image";
                break;
            case PROCESS_IMAGE:
                friendlyName = "Process Image";
                break;
            case FIND_ZONES:
                friendlyName = "Find Zones";
                break;
            case RECOGNIZE_FIRST_PASS:
                friendlyName = "Recognize First Pass";
                break;
            case RECOGNIZE_SECOND_PASS:
                friendlyName = "Recognize Second Pass";
                break;
            case RECOGNIZE_THIRD_PASS:
                friendlyName = "Recognize Third Pass";
                break;
            case SAVE_DOCUMENT_PREPARE:
                friendlyName = "Save Document Prepare";
                break;
            case SAVE_DOCUMENT:
                friendlyName = "SaveDocument";
                break;
            case SAVE_DOCUMENT_CONVERT_IMAGE:
                friendlyName = "SaveDocumentConvertImage";
                break;
            case FORMATTING:
                friendlyName = "Formatting";
                break;
            case RECOGNIZE_OMR:
                friendlyName = "Recognize Omr";
                break;

            default:
                break;
        }
        return friendlyName;
    }

    private class RecognizeTextTaskParams {
        private RasterImage mImage;
        private LeadRect mRect;

        public RecognizeTextTaskParams(RasterImage image, LeadRect rc) {
            mImage = image;
            mRect = rc;


        }

        public RasterImage getImage() {
            return mImage;
        }

        public LeadRect getRect() {
            return mRect;
        }

    }

    private class RecognizeTextTask extends AsyncTask<RecognizeTextTaskParams, Void, String> implements OcrProgressListener, DialogInterface.OnClickListener {
        private boolean mAbort = false;

        protected void onPreExecute() {
            try {
                mIsWorking = true;
                // Show progress
                mProgressDlg = Progress.create(MainActivity.this, "Recognize", "Start...", true);
                mProgressDlg.setProgress(0);
                mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", this);
                 mProgressDlg.show();
                mAbort = false;
            } catch (Exception ex) {

            }

        }

        //------------------------------------------------------------------------------------------------------------------
        protected String doInBackground(RecognizeTextTaskParams... paramArray) {
            RecognizeTextTaskParams params = paramArray[0];
            RasterImage image = null;
            try {
                image = params.getImage();
                OcrDocument document = mOcrEngine.getDocumentManager().createDocument();
                if (mAbort)
                    return null;

                OcrPage ocrPage = document.getPages().addPage(image, this);

                if (mAbort)
                    return null;

                LeadRect rc = params.getRect();
                if (rc != null) {
                    OcrZone zone = new OcrZone();
                    zone.setBounds(rc);
                    ocrPage.getZones().add(zone);
                    DesignationResultat = ocrPage.recognizeText(this);
                                }

                if (mAutoInvertEnabled) {
                    ocrPage.autoPreprocess(OcrAutoPreprocessPageCommand.INVERT, this);
                    if (mAbort)
                        return null;
                }

                ocrPage.autoPreprocess(OcrAutoPreprocessPageCommand.DESKEW, this);
                if (mAbort)
                    return null;
                String pageText = ocrPage.recognizeText(this);
                return pageText;


            } catch (Exception ex) {
                return null;
            } finally {
                if (image != null)
                    image.dispose();
            }

        }

        //--------------------------------------------------------------------------------------------------------------
        protected void onPostExecute(String result) {
            Progress.close(mProgressDlg);
            mIsWorking = false;
            if (mAbort) {
                return;
            }
            if (result == null)
                Messager.showError(MainActivity.this, "Error recognizing page", null);
            else {
                OcrResult = result;
                readRawTextFile(getApplicationContext(), R.raw.plancomptable1);

                Toast.makeText(getApplicationContext(), OcrResult.toString(),
                        Toast.LENGTH_LONG).show();
                Log.i("Resultat OCR", OcrResult);
                if(OcrResult!=null&&DesignationResultat==null) {
                    ParsingOcrResult Parse = ParsingOcrResult.parsingOcr(OcrResult);
                    OcrWordIntent= Parse.ocrWords;
                    TestTel=Parse.TestTel;

                    TestNfacture=Parse.TestNfacture;
                    TestMatriculeFiscal=Parse.TestMatriculeFiscale;
                    TestMF=Parse.TestMF;
                    TestTva=Parse.TestTVA;
                     TestTottal=Parse.TestTottalTTC;
                     TestHTVA=Parse.TestHtva;
                     TestFax=Parse.TestFax;
                     TestEmail=Parse.TestMail;
                     TestSie=Parse.TestSite;
                     TestDate=Parse.TestDate;
                    String AA = Parse.NoFacture + Parse.MatriculeFiscale + Parse.Email + Parse.Fax + Parse.TVA;
                 ch= new ChoixPhotoResult(Parse.NoFacture, Parse.MatriculeFiscale, Parse.MF, Parse.TVA, Parse.Tottal, Parse.HTVA, Parse.Tel, Parse.Fax, Parse.Email, Parse.SiteWeb, Parse.Date, Parse.Designation, DesignationResultat);

                }
            }
           if (OcrResult != null && DesignationResultat != null) {


                Intent intent = new Intent(MainActivity.this,
                        Formulaire.class);
               intent.putExtra("ch", ch);
               intent.putExtra("TestTel",TestTel);
               intent.putExtra("TestNfacture",TestNfacture);
               intent.putExtra("TestMatriculeFiscal",TestMatriculeFiscal);
               intent.putExtra("TestMF",TestMF);
               intent.putExtra("TestTva",TestTva);
               intent.putExtra("TestTottal",TestTottal);
               intent.putExtra("TestHTVA",TestHTVA);
               intent.putExtra("TestFax",TestFax);
               intent.putExtra("TestEmail",TestEmail);
               intent.putExtra("TestSie",TestSie);
               intent.putExtra("TestDate",TestDate);
               intent.putExtra("OcrWordIntent", OcrWordIntent);
               intent.putExtra("Designation", DesignationResultat);
               DesignationResultat=null;
                startActivity(intent);
                finish();

            }
        }

        @Override
        public void onProgressEvent(OcrProgressEvent event) {
            OcrProgressData data = event.getData();
            data.setStatus(mAbort ? OcrProgressStatus.ABORT : OcrProgressStatus.CONTINUE);
            if (mAbort)
                return;
            final String message = getOperationFriendlyName(data.getOperation());
            final int progress = data.getPercentage();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDlg.setProgress(progress);
                    mProgressDlg.setMessage(message);
                }
            });
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            mAbort = true;
            Progress.close(mProgressDlg);

            mProgressDlg = Progress.show(MainActivity.this, "Recognize", "Aborting...");
        }
       public String readRawTextFile(Context ctx, int resId)
        {
            try {
                Dictionnaire = new ArrayList<String>();
                InputStream inputStream = ctx.getResources().openRawResource(resId);

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line=reader.readLine();
                text = new StringBuilder();


                while (( line = reader.readLine()) != null) {
                    String[] data = line.split("\\=");
                    for(int i=0;i<data.length;i++){
                        Dictionnaire.add(data[i]);
                    }
                }
                for (String ss : Dictionnaire) {

                   // System.out.println(ss);
                }
                reader.close();
            } catch (IOException e) {
                return null;
            }
            //System.out.println("txt  " + text.toString());
            return text.toString();

        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("returnKey1", "Swinging on a star. ");
        data.putExtra("returnKey2", "You could be better then you are. ");
        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        super.finish();
    }
    private Bitmap adjustedContrast(Bitmap src, double value)
    {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap

        // create a mutable empty bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        // create a canvas so that we can draw the bmOut Bitmap from source bitmap
        Canvas c = new Canvas();
        c.setBitmap(bmOut);

        // draw bitmap to bmOut from src bitmap so we can modify it
        c.drawBitmap(src, 0, 0, new Paint(Color.BLACK));


        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.green(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.blue(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}