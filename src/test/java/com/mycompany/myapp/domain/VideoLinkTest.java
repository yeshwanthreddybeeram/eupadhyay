package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class VideoLinkTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VideoLink.class);
        VideoLink videoLink1 = new VideoLink();
        videoLink1.setId(1L);
        VideoLink videoLink2 = new VideoLink();
        videoLink2.setId(videoLink1.getId());
        assertThat(videoLink1).isEqualTo(videoLink2);
        videoLink2.setId(2L);
        assertThat(videoLink1).isNotEqualTo(videoLink2);
        videoLink1.setId(null);
        assertThat(videoLink1).isNotEqualTo(videoLink2);
    }
}
