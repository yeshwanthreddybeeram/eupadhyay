package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MyVideosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyVideos.class);
        MyVideos myVideos1 = new MyVideos();
        myVideos1.setId(1L);
        MyVideos myVideos2 = new MyVideos();
        myVideos2.setId(myVideos1.getId());
        assertThat(myVideos1).isEqualTo(myVideos2);
        myVideos2.setId(2L);
        assertThat(myVideos1).isNotEqualTo(myVideos2);
        myVideos1.setId(null);
        assertThat(myVideos1).isNotEqualTo(myVideos2);
    }
}
