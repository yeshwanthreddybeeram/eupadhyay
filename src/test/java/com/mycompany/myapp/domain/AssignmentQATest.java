package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AssignmentQATest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssignmentQA.class);
        AssignmentQA assignmentQA1 = new AssignmentQA();
        assignmentQA1.setId(1L);
        AssignmentQA assignmentQA2 = new AssignmentQA();
        assignmentQA2.setId(assignmentQA1.getId());
        assertThat(assignmentQA1).isEqualTo(assignmentQA2);
        assignmentQA2.setId(2L);
        assertThat(assignmentQA1).isNotEqualTo(assignmentQA2);
        assignmentQA1.setId(null);
        assertThat(assignmentQA1).isNotEqualTo(assignmentQA2);
    }
}
