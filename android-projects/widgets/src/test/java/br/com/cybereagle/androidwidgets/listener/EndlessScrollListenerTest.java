package br.com.cybereagle.androidwidgets.listener;

import android.widget.ListView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class EndlessScrollListenerTest {

    private ListView listView;
    private EndlessScrollListener endlessScrollListener;

    @Before
    public void setUp() throws Exception {
        listView = mock(ListView.class);

        when(listView.getHeaderViewsCount()).thenReturn(0);
        when(listView.getFooterViewsCount()).thenReturn(0);


        endlessScrollListener = spy(new TestEndlessScrollListener(5));
    }

    @Test
    public void shouldLoadMoreDataOnScrollBeyondTheThreshold() {
        doReturn(true).when(endlessScrollListener).hasMoreDataToLoad();

        assertTrue(endlessScrollListener.isLoading());

        endlessScrollListener.onScroll(listView, 2, 5, 20);

        assertFalse(endlessScrollListener.isLoading());

        endlessScrollListener.onScroll(listView, 15, 5, 20);

        assertTrue(endlessScrollListener.isLoading());


        endlessScrollListener.onScroll(listView, 21, 5, 40);

        assertFalse(endlessScrollListener.isLoading());

        endlessScrollListener.onScroll(listView, 35, 5, 40);

        assertTrue(endlessScrollListener.isLoading());

        endlessScrollListener.onScroll(listView, 38, 5, 60);

        assertFalse(endlessScrollListener.isLoading());

        doReturn(false).when(endlessScrollListener).hasMoreDataToLoad();

        endlessScrollListener.onScroll(listView, 55, 5, 60);

        assertFalse(endlessScrollListener.isLoading());


        verify(endlessScrollListener, atMost(6)).hasMoreDataToLoad();
        verify(endlessScrollListener, times(1)).loadMoreData(1);
        verify(endlessScrollListener, times(1)).loadMoreData(2);
        verify(endlessScrollListener, never()).loadMoreData(3);
    }

    private static class TestEndlessScrollListener extends EndlessScrollListener {

        private TestEndlessScrollListener(int visibleThreshold) {
            super(visibleThreshold);
        }

        @Override
        protected boolean hasMoreDataToLoad() {
            return false;
        }

        @Override
        protected void loadMoreData(int page) {

        }
    }
}
