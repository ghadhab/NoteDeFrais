package com.compta.firstak.notedefrais;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import leadtools.LeadEvent;
import leadtools.LeadRect;
import leadtools.LeadSizeF;
import leadtools.controls.CoordinateType;
import leadtools.controls.ImageViewer;
import leadtools.controls.ImageViewerInteractiveMode;
import leadtools.controls.ImageViewerSizeMode;
import leadtools.controls.InteractiveService;
import leadtools.controls.InteractiveSimpleOnGestureListener;

enum RectangleActivePoint {
   NONE(0), TOP_LEFT(1), TOP_RIGHT(2), BOTTOM_LEFT(3), BOTTOM_RIGHT(4), RECT_BODY(5);

   private int intValue;

   private RectangleActivePoint(int value) {
      intValue = value;
   }

   public int getValue() {
      return intValue;
   }
};

public class SelectAreaInteractiveMode extends ImageViewerInteractiveMode {
   static final int HITTEST = 20;
   static final int THUMB_LENGTH = 10;

   @Override
   public String getName() {
      return "Select Area";
   }

   private RectF mImageRectangle;
   private RectF mSelectedArea;
   private RectangleActivePoint mActivePoint;
   private Paint mPaint;
   private Paint mThumbsPaint;
   private View mShapeView;
   private InteractiveSimpleOnGestureListener mInteractiveSimpleOnGestureListener;

   private void createInteractiveDrawingView(ImageViewer viewer) {
      if (mShapeView != null)
         return;

      mShapeView = new View(viewer.getContext()) {
         @Override
         protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            ImageViewer viewer = getImageViewer();
            if (!viewer.hasImage())
               return;
            mSelectedArea = viewer.convertRect(CoordinateType.IMAGE, CoordinateType.CONTROL, mImageRectangle);
         }

         @Override
         public void onDraw(Canvas canvas) {
            canvas.save();
            canvas.clipRect(mSelectedArea,  Op.DIFFERENCE);
            canvas.drawARGB(128, 128, 128, 128);
            canvas.restore();
            
            // Draw selected area rectangle 
            canvas.drawRect(mSelectedArea, mPaint);
            
            // Draw thumbs
            canvas.drawRect(mSelectedArea.left - THUMB_LENGTH, mSelectedArea.top - THUMB_LENGTH, mSelectedArea.left + THUMB_LENGTH, mSelectedArea.top + THUMB_LENGTH, mThumbsPaint);
            canvas.drawRect(mSelectedArea.left - THUMB_LENGTH, mSelectedArea.bottom - THUMB_LENGTH, mSelectedArea.left + THUMB_LENGTH, mSelectedArea.bottom + THUMB_LENGTH, mThumbsPaint);
            canvas.drawRect(mSelectedArea.right - THUMB_LENGTH, mSelectedArea.top - THUMB_LENGTH, mSelectedArea.right + THUMB_LENGTH, mSelectedArea.top + THUMB_LENGTH, mThumbsPaint);
            canvas.drawRect(mSelectedArea.right - THUMB_LENGTH, mSelectedArea.bottom - THUMB_LENGTH, mSelectedArea.right + THUMB_LENGTH, mSelectedArea.bottom + THUMB_LENGTH, mThumbsPaint);
         }
      };

      mShapeView.setBackgroundColor(Color.TRANSPARENT);
      viewer.addView(mShapeView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
   }

   private void destroyInteractiveDrawingView(ImageViewer viewer) {
      if (mShapeView == null)
         return;

      viewer.removeView(mShapeView);
      mShapeView = null;
   }

   public SelectAreaInteractiveMode() {
      // Selected rectangle paint
      mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      mPaint.setStyle(Style.STROKE);
      mPaint.setColor(Color.RED);
      mPaint.setStrokeWidth(2f);
      
      // Thumbs paint
      mThumbsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      mThumbsPaint.setStyle(Style.FILL_AND_STROKE);
      mThumbsPaint.setColor(Color.BLUE);

      mSelectedArea = new RectF();
      mImageRectangle = new RectF();
      mActivePoint = RectangleActivePoint.NONE;

      // Create a simple gesture listener to listen for down, move and up events
      mInteractiveSimpleOnGestureListener = new InteractiveSimpleOnGestureListener() {
         private PointF mLastPoint;

         @Override
         public void onDown(Object source, MotionEvent event) {
            if (!canStartWork(event))
               return;

            ImageViewer viewer = getImageViewer();
            if (!viewer.hasImage())
               return;

            if (!isWorking()) {
               onWorkStarted(LeadEvent.getEmpty(this));
            }
            
            mLastPoint = new PointF(event.getX(), event.getY());
         }

         @Override
         public void onMove(Object source, MotionEvent event) {
            if (isWorking()) {
               ImageViewer viewer = getImageViewer();
               
               float x = event.getX();
               float y = event.getY();

               LeadSizeF size = viewer.getImageSize();
               RectF rect = viewer.convertRect(CoordinateType.IMAGE, CoordinateType.CONTROL, new RectF(0, 0, size.getWidth(), size.getHeight()));
               RectF newSelectedArea = new RectF(mSelectedArea);
               // Update rectangle bounds
               switch (mActivePoint) {
               case TOP_LEFT:
                  newSelectedArea.top = y; 
                  newSelectedArea.left = x;
                  break;
               case TOP_RIGHT:
                  newSelectedArea.top = y; 
                  newSelectedArea.right = x;
                  break;
               case BOTTOM_LEFT:
                  newSelectedArea.bottom = y; 
                  newSelectedArea.left = x;
                  break;
               case BOTTOM_RIGHT:
                  newSelectedArea.bottom = y; 
                  newSelectedArea.right = x;
                  break;
               case RECT_BODY:
                  float xDiff = x - mLastPoint.x;
                  float yDiff = y - mLastPoint.y;
                  newSelectedArea.top += yDiff;
                  newSelectedArea.bottom += yDiff;
                  newSelectedArea.left += xDiff;
                  newSelectedArea.right += xDiff;
               default:
                  break;
               }
               
               // Limit the selected area rectangle to image bounds
               if(newSelectedArea.top > rect.top && newSelectedArea.bottom < rect.bottom && newSelectedArea.top < newSelectedArea.bottom) {
                  mSelectedArea.top = newSelectedArea.top;
                  mSelectedArea.bottom = newSelectedArea.bottom;
               }
               if(newSelectedArea.left > rect.left && newSelectedArea.right < rect.right && newSelectedArea.left < newSelectedArea.right) {
                  mSelectedArea.left = newSelectedArea.left;
                  mSelectedArea.right = newSelectedArea.right;
               }
               
               mImageRectangle = viewer.convertRect(CoordinateType.CONTROL, CoordinateType.IMAGE, mSelectedArea);
               mLastPoint = new PointF(x, y);
               invalidateInteractiveView();
            }
         }

         @Override
         public void onUp(Object source, MotionEvent event) {
            if (isWorking()) {
               onWorkCompleted(LeadEvent.getEmpty(this));
               mActivePoint = RectangleActivePoint.NONE;
               invalidateInteractiveView();
            }
         }
      };
   }

   private void invalidateInteractiveView() {
      if (mShapeView == null)
         return;

      mShapeView.invalidate();
   }

   @Override
   protected boolean canStartWork(MotionEvent event) {
      boolean bRet = super.canStartWork(event);
      if (!bRet)
         return bRet;

      ImageViewer viewer = getImageViewer();
      if (!viewer.hasImage())
         return false;

      PointF pt = new PointF(event.getX(), event.getY());

      RectangleActivePoint currentActivePoint = findActivePoint(pt);
      if (currentActivePoint != RectangleActivePoint.NONE) {
         mActivePoint = currentActivePoint;
         return true;
      } else
         return false;
   }

   @Override
   public void start(ImageViewer viewer) {
      super.start(viewer);
      InteractiveService service = super.getInteractiveService();
      service.addSimpleGuestureListener(mInteractiveSimpleOnGestureListener);
      if (!viewer.hasImage())
         return;

      setWorkOnImageRectangle(false);
      // Fit the image to the viewer
      viewer.zoom(ImageViewerSizeMode.FIT, 1, viewer.getDefaultZoomOrigin());


      LeadSizeF size = viewer.getImageSize();
      mImageRectangle = new RectF(size.getWidth() * 0.25f, size.getHeight() * 0.25f, size.getWidth() * 0.75f, size.getHeight() * 0.75f);
      mSelectedArea = viewer.convertRect(CoordinateType.IMAGE, CoordinateType.CONTROL, mImageRectangle);
      
      createInteractiveDrawingView(viewer);
   }

   @Override
   public void stop(ImageViewer viewer) {
      if (isStarted()) {
         InteractiveService service = super.getInteractiveService();
         service.removeSimpleGuestureListener(mInteractiveSimpleOnGestureListener);

         destroyInteractiveDrawingView(viewer);
         viewer.postInvalidate();
         super.stop(viewer);
      }
   }

   public LeadRect getSelectedImageRectangle() {
      return LeadRect.fromLTRB((int) mImageRectangle.left, (int) mImageRectangle.top, (int) mImageRectangle.right, (int) mImageRectangle.bottom);
   }

   public RectangleActivePoint getActivePoint() {
      return mActivePoint;
   }

   private float distanceBetween(PointF pt1, PointF pt2) {
      return (float) Math.sqrt((pt1.x - pt2.x) * (pt1.x - pt2.x) + (pt1.y - pt2.y) * (pt1.y - pt2.y));
   }

   private RectangleActivePoint findActivePoint(PointF pt) {
      // Check the point
      if (distanceBetween(pt, new PointF(mSelectedArea.left, mSelectedArea.top)) < HITTEST)
         return RectangleActivePoint.TOP_LEFT;
      else if (distanceBetween(pt, new PointF(mSelectedArea.right, mSelectedArea.top)) < HITTEST)
         return RectangleActivePoint.TOP_RIGHT;
      else if (distanceBetween(pt, new PointF(mSelectedArea.left, mSelectedArea.bottom)) < HITTEST)
         return RectangleActivePoint.BOTTOM_LEFT;
      else if (distanceBetween(pt, new PointF(mSelectedArea.right, mSelectedArea.bottom)) < HITTEST)
         return RectangleActivePoint.BOTTOM_RIGHT;
      else if(mSelectedArea.contains(pt.x, pt.y))
         return RectangleActivePoint.RECT_BODY;
         
      return RectangleActivePoint.NONE;
   }
}
