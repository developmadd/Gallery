package com.madd.madd.gallery.UserGallery.Gallery;

import android.content.Context;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GalleryPresenterTest {

    private GalleryPresenter presenter;

    private GalleryContract.Model mockedModel;
    private GalleryContract.View mockedView;

    @Before
    public void setUp(){

        mockedModel = mock(GalleryContract.Model.class);
        mockedView = mock(GalleryContract.View.class);


        presenter = new GalleryPresenter(mockedModel);
        presenter.setView(mockedView);
    }

    /*

        Tests:
        1: Request album list without permissions
        2: Request album list with permissions but there is not any album
        3: Request album list with permissions and successfully
        4: Update the picture list selected by user

     */

    @Test
    public void requestAlbumListWithoutPermissions() {
        when(mockedView.areReadPermissionGranted()).thenReturn(false);
        presenter.requestAlbumList();
        verify(mockedView).showPermissionError();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            verify(mockedView).requestReadPermission();
        }
    }

    @Test
    public void requestAlbumListWithPermissionsAndEmptyAlbumList() {
        when(mockedView.areReadPermissionGranted()).thenReturn(true);
        presenter.requestAlbumList();
        doAnswer(invocation -> {
            ((GalleryContract.Model.AlbumListRequest)invocation.getArguments()[0]).onError("");
            return null;
        }).when(mockedModel).getAlbumList(any(GalleryContract.Model.AlbumListRequest.class));



        verify(mockedView).showEmptyListError();
    }

    @Test
    public void updateSelectedPictureList() {
    }
}