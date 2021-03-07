package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class ClassIdentityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassIdentity.class);
        ClassIdentity classIdentity1 = new ClassIdentity();
        classIdentity1.setId(1L);
        ClassIdentity classIdentity2 = new ClassIdentity();
        classIdentity2.setId(classIdentity1.getId());
        assertThat(classIdentity1).isEqualTo(classIdentity2);
        classIdentity2.setId(2L);
        assertThat(classIdentity1).isNotEqualTo(classIdentity2);
        classIdentity1.setId(null);
        assertThat(classIdentity1).isNotEqualTo(classIdentity2);
    }
}
