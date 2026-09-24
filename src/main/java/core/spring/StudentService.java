package core.spring;

import core.spring.dto.CreateStudentRequestDto;
import core.spring.dto.UpdateStudentRequestDto;
import core.spring.dto.UpdateStudentResponseDto;
import core.spring.entitiy.Student;
import core.spring.dto.CreateStudentResponseDto;
import core.spring.exception.DuplicateResourceException;
import core.spring.exception.ResourceNotFoundException;
import core.spring.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto){
        Student student = mapToEntity(studentReqDto);

        if(emailExists(student)){
            throw new DuplicateResourceException("Student with email "+student.getEmail()+" already exists");
        }
        Student studentResp = studentRepository.save(student);

        return mapToDto(studentResp);
    }

    public CreateStudentResponseDto getStudent(Long id){

        Student studentResp = studentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Student with id "+id+" not found"));

        return mapToDto(studentResp);

    }

    public List<Student> getAllStudent(){
        List<Student> student = studentRepository.findByDeletedIsFalse();
        return student;
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto updateStudentReqDto){
        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id).orElseThrow(()->
                        new ResourceNotFoundException("Student with id "+id+" not found"));

        existingStudent.setName(updateStudentReqDto.getName());
        existingStudent.setRollNo(updateStudentReqDto.getRollNo());
        existingStudent.setSubject(updateStudentReqDto.getSubject());
        existingStudent.setAge(updateStudentReqDto.getAge());
        existingStudent.setDeleted(false);

        Student saveStudent = studentRepository.save(existingStudent);

        return mapToUpdateDto(saveStudent);
    }


    public void deleteStudent(Long id){
        Student studentToBeDeleted = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student with id "+id+" not found"));

        studentRepository.delete(studentToBeDeleted);
    }

    public void deleteStudentSoftly(Long id){

        Student studentToBeDeleted = studentRepository.findByIdAndDeletedIsFalse(id).
                orElseThrow(()-> new ResourceNotFoundException("Student with id "+id+" not found"));


        studentToBeDeleted.setDeleted(true);

        studentRepository.save(studentToBeDeleted);


    }

    private Student mapToEntity(CreateStudentRequestDto studentRequestDto){
                Student student = new Student();

                student.setName(studentRequestDto.getName());
                student.setAge(studentRequestDto.getAge());
                student.setDeleted(false);
                student.setEmail(studentRequestDto.getEmail());
                student.setSubject(studentRequestDto.getSubject());
                student.setRollNo(studentRequestDto.getRollNo());
                student.setCreatedAt(LocalDateTime.now());
                student.setUpdatedAt(LocalDateTime.now());

                return student;
    }

    private CreateStudentResponseDto mapToDto(Student student){

        CreateStudentResponseDto responseDto = new CreateStudentResponseDto();

        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setEmail(student.getEmail());
        responseDto.setMessage("Student saved successfully");
        responseDto.setCreatedAt(LocalDateTime.now());
        responseDto.setUpdatedAt(LocalDateTime.now());

        return responseDto;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student){
           UpdateStudentResponseDto updateStudentResponseDto = new UpdateStudentResponseDto();

           updateStudentResponseDto.setId(student.getId());
           updateStudentResponseDto.setAge(student.getAge());
           updateStudentResponseDto.setName(student.getName());
           updateStudentResponseDto.setRollNo(student.getAge());
           updateStudentResponseDto.setSubject(student.getSubject());
           updateStudentResponseDto.setUpdatedAt(student.getUpdatedAt());
           updateStudentResponseDto.setEmail(student.getEmail());
           updateStudentResponseDto.setMessage("Student updated successfully");

           return updateStudentResponseDto;


    }

    private Boolean emailExists(Student student){
        return studentRepository.existsByEmail(student.getEmail());
    }

}
