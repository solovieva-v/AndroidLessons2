package ru.mirea.solovieva.mireaproject.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.solovieva.mireaproject.R;
import ru.mirea.solovieva.mireaproject.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3};
    private int currentIndex = 0;
    private ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        imageView = root.findViewById(R.id.imageView2);
        imageView.setOnClickListener(this::onClick);
        updateImage();

        return root;
    }
    public void onClick(View v) {
        currentIndex = (currentIndex + 1) % images.length;
        updateImage();
    }

    private void updateImage() {
        imageView.setImageResource(images[currentIndex]);
    }
}
