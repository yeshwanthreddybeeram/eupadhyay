package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class StudyMaterialsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudyMaterials.class);
        StudyMaterials studyMaterials1 = new StudyMaterials();
        studyMaterials1.setId(1L);
        StudyMaterials studyMaterials2 = new StudyMaterials();
        studyMaterials2.setId(studyMaterials1.getId());
        assertThat(studyMaterials1).isEqualTo(studyMaterials2);
        studyMaterials2.setId(2L);
        assertThat(studyMaterials1).isNotEqualTo(studyMaterials2);
        studyMaterials1.setId(null);
        assertThat(studyMaterials1).isNotEqualTo(studyMaterials2);
    }
}
