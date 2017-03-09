package uianimationdemo.com.example.daysnews.slidingmenu;

import android.graphics.Canvas;
import android.view.animation.Interpolator;


public class CanvasTransformerBuilder {
	// Transformer ��ѹ��
	private SlidingMenu.CanvasTransformer mTrans;

	private static Interpolator lin = new Interpolator() {
		public float getInterpolation(float t) {
			return t;
		}
	};

	private void initTransformer() {
		if (mTrans == null)
			mTrans = new SlidingMenu.CanvasTransformer() {
				public void transformCanvas(Canvas canvas, float percentOpen) {
				}
			};
	}

	/*** �Ŵ󶯻� */
	public SlidingMenu.CanvasTransformer zoom(final int openedX, final int closedX,
											  final int openedY, final int closedY, final int px, final int py) {
		return zoom(openedX, closedX, openedY, closedY, px, py, lin);
	}

	/*** �Ŵ󶯻� */
	public SlidingMenu.CanvasTransformer zoom(final int openedX, final int closedX,
											  final int openedY, final int closedY, final int px, final int py,
											  final Interpolator interp) {
		initTransformer();
		mTrans = new SlidingMenu.CanvasTransformer() {
			public void transformCanvas(Canvas canvas, float percentOpen) {
				mTrans.transformCanvas(canvas, percentOpen);
				float f = interp.getInterpolation(percentOpen);
				canvas.scale((openedX - closedX) * f + closedX,
						(openedY - closedY) * f + closedY, px, py);
			}
		};
		return mTrans;
	}

	/** ��ת���� */
	public SlidingMenu.CanvasTransformer rotate(final int openedDeg, final int closedDeg,
												final int px, final int py) {
		return rotate(openedDeg, closedDeg, px, py, lin);
	}

	/** ��ת���� */
	public SlidingMenu.CanvasTransformer rotate(final int openedDeg, final int closedDeg,
												final int px, final int py, final Interpolator interp) {
		initTransformer();
		mTrans = new SlidingMenu.CanvasTransformer() {
			public void transformCanvas(Canvas canvas, float percentOpen) {
				mTrans.transformCanvas(canvas, percentOpen);
				float f = interp.getInterpolation(percentOpen);
				canvas.rotate((openedDeg - closedDeg) * f + closedDeg, px, py);
			}
		};
		return mTrans;
	}

	/** ƽ�ƶ��� **/
	public SlidingMenu.CanvasTransformer translate(final int openedX, final int closedX,
												   final int openedY, final int closedY) {
		return translate(openedX, closedX, openedY, closedY, lin);
	}

	/** ƽ�ƶ��� **/
	public SlidingMenu.CanvasTransformer translate(final int openedX, final int closedX,
												   final int openedY, final int closedY, final Interpolator interp) {
		initTransformer();
		mTrans = new SlidingMenu.CanvasTransformer() {
			public void transformCanvas(Canvas canvas, float percentOpen) {
				mTrans.transformCanvas(canvas, percentOpen);
				float f = interp.getInterpolation(percentOpen);
				canvas.translate((openedX - closedX) * f + closedX,
						(openedY - closedY) * f + closedY);
			}
		};
		return mTrans;
	}

	public SlidingMenu.CanvasTransformer concatTransformer(final SlidingMenu.CanvasTransformer t) {
		initTransformer();
		mTrans = new SlidingMenu.CanvasTransformer() {
			public void transformCanvas(Canvas canvas, float percentOpen) {
				mTrans.transformCanvas(canvas, percentOpen);
				t.transformCanvas(canvas, percentOpen);
			}
		};
		return mTrans;
	}

}
