package com.fyp.agrifarm;//package com.fyp.agriculture;
//
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.firebase.login.api.DeveloperKey;
//import com.google.android.youtube.player.YouTubeInitializationResult;
//import com.google.android.youtube.player.YouTubePlayer;
//import com.google.android.youtube.player.YouTubePlayerSupportFragment;
//
// TODO: 2. Refactor YOUTUBE FRAGMENT: AndroidX
//public class YoutubeFragment extends Fragment {
//
//    private static final String ARG_VIDEO_URL = "vidUrl";
//    private static final String ARG_PARAM2 = "param2";
//    public static final String TAG = "YoutubeFragment";
//
//    private String videoUrl;
//    private String mParam2;
//
////    private OnFragmentInteractionListener mListener;
//
//    public YoutubeFragment() {
//        // Required empty public constructor
//    }
//
//    public static YoutubeFragment newInstance(final String videoUrl, String param2) {
//        final YoutubeFragment fragment = new YoutubeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_VIDEO_URL, videoUrl);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            videoUrl = getArguments().getString(ARG_VIDEO_URL);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.content_youtube, container,
//                false);
//
//        YouTubePlayerSupportFragment youFragment = YouTubePlayerSupportFragment.newInstance();
//
//        getChildFragmentManager().beginTransaction().replace(R.id.fragmentYoutube, youFragment).commit();
//
//        youFragment.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
//            @Override
//            public void onInitializationFailure(YouTubePlayer.Provider provider,
//                                                YouTubeInitializationResult youTubeInitializationResult) {
//                Toast.makeText(getContext(), "Couldn't load video!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onInitializationSuccess(YouTubePlayer.Provider provider,
//                                                YouTubePlayer youTubePlayer, boolean wasRestored) {
//                if (!wasRestored) {
//                    //I assume the below String value is your video id
////                    Toast.makeText(getContext(), "Video : " + videoUrl, Toast.LENGTH_SHORT).show();
//                    youTubePlayer.cueVideo(videoUrl);
//                    youTubePlayer.play();
//
////                    youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
////                        @Override
////                        public void onFullscreen(boolean b) {
////                            Log.i(TAG, "Fullscreen Changed");
////                        }
////                    });
//
//                }
//            }
//        });
//
//        return parent;
//    }
//
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        if (context instanceof OnFragmentInteractionListener) {
////            mListener = (OnFragmentInteractionListener) context;
////        } else {
////            throw new RuntimeException(context.toString()
////                    + " must implement OnFragmentInteractionListener");
////        }
////    }
//
////    @Override
////    public void onDetach() {
////        super.onDetach();
////        mListener = null;
////    }
//
////    public interface OnFragmentInteractionListener {
////        void onForecastClick(View v);
////    }
//}
