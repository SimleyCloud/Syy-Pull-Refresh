package view.library.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout;

import view.library.R;


/**
 * ClassicPullRefreshHeaderView
 * Created by SmileCloud on 2016/6/21.
 */
public class ClassicRefreshHeaderView extends SwipeRefreshHeaderLayout {

    private ImageView ivArrow;

    private ImageView ivSuccess;

    private TextView tvRefresh;

    private ProgressBar progressBar;

    private int mHeaderHeight;

    private Animation rotateUp;

    private Animation rotateDown;

    private boolean rotated = false;

    public ClassicRefreshHeaderView(Context context) {
        this(context, null);
    }

    public ClassicRefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mHeaderHeight = getResources().getDimensionPixelOffset(R.dimen.refresh_header_height_twitter);
        rotateUp = AnimationUtils.loadAnimation(context, R.anim.rotate_up);
        rotateDown = AnimationUtils.loadAnimation(context, R.anim.rotate_down);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvRefresh = (TextView) findViewById(R.id.tvRefresh);
        ivArrow = (ImageView) findViewById(R.id.ivArrow);
        ivSuccess = (ImageView) findViewById(R.id.ivSuccess);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onRefresh() {
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        progressBar.setVisibility(VISIBLE);
        tvRefresh.setText(ClassicConstant.headerRefreshing);
    }

    @Override
    public void onPrepare() {
        Log.d("RefreshHeader", "onPrepare()");
        ivArrow.setVisibility(VISIBLE);
    }

    @Override
    public void onMove(int y, boolean isComplete, boolean automatic) {
        if (!isComplete) {
            progressBar.setVisibility(GONE);
            ivSuccess.setVisibility(GONE);
            if (y > mHeaderHeight) {
                tvRefresh.setText(ClassicConstant.headerPullDown);
                if (!rotated) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(rotateUp);
                    rotated = true;
                }
            } else if (y < mHeaderHeight) {
                if (rotated) {
                    ivArrow.clearAnimation();
                    ivArrow.startAnimation(rotateDown);
                    rotated = false;
                }
                tvRefresh.setText(ClassicConstant.headerPullRelease);
            }
        }
    }

    @Override
    public void onRelease() {
        Log.d("RefreshHeader", "onRelease()");
        ivArrow.clearAnimation();
        ivArrow.setVisibility(GONE);
    }

    @Override
    public void onComplete() {
        rotated = false;
        ivSuccess.setVisibility(VISIBLE);
        ivArrow.clearAnimation();
        progressBar.setVisibility(GONE);
        tvRefresh.setText(ClassicConstant.Complete);
    }

    @Override
    public void onReset() {
        rotated = false;
        ivSuccess.setVisibility(GONE);
        ivArrow.clearAnimation();
        progressBar.setVisibility(GONE);
    }

}
