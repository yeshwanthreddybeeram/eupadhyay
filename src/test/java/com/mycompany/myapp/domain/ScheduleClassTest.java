package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ScheduleClassTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScheduleClass.class);
        ScheduleClass scheduleClass1 = new ScheduleClass();
        scheduleClass1.setId(1L);
        ScheduleClass scheduleClass2 = new ScheduleClass();
        scheduleClass2.setId(scheduleClass1.getId());
        assertThat(scheduleClass1).isEqualTo(scheduleClass2);
        scheduleClass2.setId(2L);
        assertThat(scheduleClass1).isNotEqualTo(scheduleClass2);
        scheduleClass1.setId(null);
        assertThat(scheduleClass1).isNotEqualTo(scheduleClass2);
    }
}
