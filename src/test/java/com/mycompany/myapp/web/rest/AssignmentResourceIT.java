package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.DevpctApp;
import com.mycompany.myapp.domain.Assignment;
import com.mycompany.myapp.repository.AssignmentRepository;
import com.mycompany.myapp.service.AssignmentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Assignmentstatus;
/**
 * Integration tests for the {@link AssignmentResource} REST controller.
 */
@SpringBootTest(classes = DevpctApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AssignmentResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_STUDENTLOGINNAME = "AAAAAAAAAA";
    private static final String UPDATED_STUDENTLOGINNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEELOGINNAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEELOGINNAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_SUBMITDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBMITDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_MARKS = 1;
    private static final Integer UPDATED_MARKS = 2;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Assignmentstatus DEFAULT_ASGNSTATUS = Assignmentstatus.INACTIVE;
    private static final Assignmentstatus UPDATED_ASGNSTATUS = Assignmentstatus.ACTIVE;

    private static final String DEFAULT_ASSIGNMENTLINK = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNMENTLINK = "BBBBBBBBBB";

    private static final String DEFAULT_SUBMITLINK = "AAAAAAAAAA";
    private static final String UPDATED_SUBMITLINK = "BBBBBBBBBB";

    private static final byte[] DEFAULT_ASSIGNMENTPDF = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSIGNMENTPDF = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSIGNMENTPDF_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSIGNMENTPDF_CONTENT_TYPE = "image/png";

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssignmentMockMvc;

    private Assignment assignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assignment createEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .subject(DEFAULT_SUBJECT)
            .description(DEFAULT_DESCRIPTION)
            .studentloginname(DEFAULT_STUDENTLOGINNAME)
            .employeeloginname(DEFAULT_EMPLOYEELOGINNAME)
            .status(DEFAULT_STATUS)
            .submitdate(DEFAULT_SUBMITDATE)
            .marks(DEFAULT_MARKS)
            .remarks(DEFAULT_REMARKS)
            .asgnstatus(DEFAULT_ASGNSTATUS)
            .assignmentlink(DEFAULT_ASSIGNMENTLINK)
            .submitlink(DEFAULT_SUBMITLINK)
            .assignmentpdf(DEFAULT_ASSIGNMENTPDF)
            .assignmentpdfContentType(DEFAULT_ASSIGNMENTPDF_CONTENT_TYPE);
        return assignment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assignment createUpdatedEntity(EntityManager em) {
        Assignment assignment = new Assignment()
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .studentloginname(UPDATED_STUDENTLOGINNAME)
            .employeeloginname(UPDATED_EMPLOYEELOGINNAME)
            .status(UPDATED_STATUS)
            .submitdate(UPDATED_SUBMITDATE)
            .marks(UPDATED_MARKS)
            .remarks(UPDATED_REMARKS)
            .asgnstatus(UPDATED_ASGNSTATUS)
            .assignmentlink(UPDATED_ASSIGNMENTLINK)
            .submitlink(UPDATED_SUBMITLINK)
            .assignmentpdf(UPDATED_ASSIGNMENTPDF)
            .assignmentpdfContentType(UPDATED_ASSIGNMENTPDF_CONTENT_TYPE);
        return assignment;
    }

    @BeforeEach
    public void initTest() {
        assignment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssignment() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();
        // Create the Assignment
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isCreated());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testAssignment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssignment.getStudentloginname()).isEqualTo(DEFAULT_STUDENTLOGINNAME);
        assertThat(testAssignment.getEmployeeloginname()).isEqualTo(DEFAULT_EMPLOYEELOGINNAME);
        assertThat(testAssignment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAssignment.getSubmitdate()).isEqualTo(DEFAULT_SUBMITDATE);
        assertThat(testAssignment.getMarks()).isEqualTo(DEFAULT_MARKS);
        assertThat(testAssignment.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testAssignment.getAsgnstatus()).isEqualTo(DEFAULT_ASGNSTATUS);
        assertThat(testAssignment.getAssignmentlink()).isEqualTo(DEFAULT_ASSIGNMENTLINK);
        assertThat(testAssignment.getSubmitlink()).isEqualTo(DEFAULT_SUBMITLINK);
        assertThat(testAssignment.getAssignmentpdf()).isEqualTo(DEFAULT_ASSIGNMENTPDF);
        assertThat(testAssignment.getAssignmentpdfContentType()).isEqualTo(DEFAULT_ASSIGNMENTPDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createAssignmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // Create the Assignment with an existing ID
        assignment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentMockMvc.perform(post("/api/assignments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAssignments() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get all the assignmentList
        restAssignmentMockMvc.perform(get("/api/assignments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignment.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].studentloginname").value(hasItem(DEFAULT_STUDENTLOGINNAME)))
            .andExpect(jsonPath("$.[*].employeeloginname").value(hasItem(DEFAULT_EMPLOYEELOGINNAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].submitdate").value(hasItem(DEFAULT_SUBMITDATE.toString())))
            .andExpect(jsonPath("$.[*].marks").value(hasItem(DEFAULT_MARKS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].asgnstatus").value(hasItem(DEFAULT_ASGNSTATUS.toString())))
            .andExpect(jsonPath("$.[*].assignmentlink").value(hasItem(DEFAULT_ASSIGNMENTLINK)))
            .andExpect(jsonPath("$.[*].submitlink").value(hasItem(DEFAULT_SUBMITLINK)))
            .andExpect(jsonPath("$.[*].assignmentpdfContentType").value(hasItem(DEFAULT_ASSIGNMENTPDF_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assignmentpdf").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSIGNMENTPDF))));
    }
    
    @Test
    @Transactional
    public void getAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.saveAndFlush(assignment);

        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", assignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignment.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.studentloginname").value(DEFAULT_STUDENTLOGINNAME))
            .andExpect(jsonPath("$.employeeloginname").value(DEFAULT_EMPLOYEELOGINNAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.submitdate").value(DEFAULT_SUBMITDATE.toString()))
            .andExpect(jsonPath("$.marks").value(DEFAULT_MARKS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.asgnstatus").value(DEFAULT_ASGNSTATUS.toString()))
            .andExpect(jsonPath("$.assignmentlink").value(DEFAULT_ASSIGNMENTLINK))
            .andExpect(jsonPath("$.submitlink").value(DEFAULT_SUBMITLINK))
            .andExpect(jsonPath("$.assignmentpdfContentType").value(DEFAULT_ASSIGNMENTPDF_CONTENT_TYPE))
            .andExpect(jsonPath("$.assignmentpdf").value(Base64Utils.encodeToString(DEFAULT_ASSIGNMENTPDF)));
    }
    @Test
    @Transactional
    public void getNonExistingAssignment() throws Exception {
        // Get the assignment
        restAssignmentMockMvc.perform(get("/api/assignments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssignment() throws Exception {
        // Initialize the database
        assignmentService.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment
        Assignment updatedAssignment = assignmentRepository.findById(assignment.getId()).get();
        // Disconnect from session so that the updates on updatedAssignment are not directly saved in db
        em.detach(updatedAssignment);
        updatedAssignment
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .studentloginname(UPDATED_STUDENTLOGINNAME)
            .employeeloginname(UPDATED_EMPLOYEELOGINNAME)
            .status(UPDATED_STATUS)
            .submitdate(UPDATED_SUBMITDATE)
            .marks(UPDATED_MARKS)
            .remarks(UPDATED_REMARKS)
            .asgnstatus(UPDATED_ASGNSTATUS)
            .assignmentlink(UPDATED_ASSIGNMENTLINK)
            .submitlink(UPDATED_SUBMITLINK)
            .assignmentpdf(UPDATED_ASSIGNMENTPDF)
            .assignmentpdfContentType(UPDATED_ASSIGNMENTPDF_CONTENT_TYPE);

        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAssignment)))
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getStudentloginname()).isEqualTo(UPDATED_STUDENTLOGINNAME);
        assertThat(testAssignment.getEmployeeloginname()).isEqualTo(UPDATED_EMPLOYEELOGINNAME);
        assertThat(testAssignment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAssignment.getSubmitdate()).isEqualTo(UPDATED_SUBMITDATE);
        assertThat(testAssignment.getMarks()).isEqualTo(UPDATED_MARKS);
        assertThat(testAssignment.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testAssignment.getAsgnstatus()).isEqualTo(UPDATED_ASGNSTATUS);
        assertThat(testAssignment.getAssignmentlink()).isEqualTo(UPDATED_ASSIGNMENTLINK);
        assertThat(testAssignment.getSubmitlink()).isEqualTo(UPDATED_SUBMITLINK);
        assertThat(testAssignment.getAssignmentpdf()).isEqualTo(UPDATED_ASSIGNMENTPDF);
        assertThat(testAssignment.getAssignmentpdfContentType()).isEqualTo(UPDATED_ASSIGNMENTPDF_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentMockMvc.perform(put("/api/assignments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(assignment)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAssignment() throws Exception {
        // Initialize the database
        assignmentService.save(assignment);

        int databaseSizeBeforeDelete = assignmentRepository.findAll().size();

        // Delete the assignment
        restAssignmentMockMvc.perform(delete("/api/assignments/{id}", assignment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
