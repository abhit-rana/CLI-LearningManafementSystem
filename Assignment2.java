import java.util.*;
import java.text.*;

public class Assignment2 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int response;
		Date dNow = null;
		SimpleDateFormat fmt = new SimpleDateFormat("E MMM d H:mm:ss z y");
		DiscussionForum discussionForum = new DiscussionForum(input, dNow, fmt);
		InstructorPortal instructorPortal = new InstructorPortal(input, dNow, fmt, discussionForum);
		StudentPortal studentPortal = new StudentPortal(input, discussionForum);
		studentPortal.initializeInstructorPortal(instructorPortal);
		instructorPortal.initializeStudentPortal(studentPortal);
		while(true) {
			System.out.print("Welcome to Backpack\n1 Enter as Instructor\n2 Enter as Student\n3 Exit\n");
			response = input.nextInt();
			if(response==1) {
				instructorPortal.menu();
			}else if(response==2) {
				studentPortal.menu();
			}else {
				break;
			}
		}
	}

}

class Comment{
	private String _message;
	private String _uploadee;
	private String _dateOfUpload;
	
	Comment(String _uploadee, String _dateOfUpload){
		this._uploadee = _uploadee;
		this._dateOfUpload = _dateOfUpload;
	}
	
	public void setMessage(String _message) {
		this._message = _message; 
	}
	
	public void display() {
		System.out.println();
		System.out.println(_message + " -"+_uploadee + "\n" + _dateOfUpload);
	}
	
}

class DiscussionForum{
	private Date dNow;
	private SimpleDateFormat fmt;
	Scanner input;
	ArrayList<Comment> _comments = new ArrayList<>();
	
	public DiscussionForum(Scanner input, Date dNow, SimpleDateFormat fmt) {
		this.input = input;
		this.dNow = dNow;
		this.fmt = fmt;
	}
	
	public void addComment(String _uploadee) {
		dNow = new Date();
		Comment objComment = new Comment(_uploadee, fmt.format(dNow));
		System.out.print("Enter the Comment:");
		input.nextLine();
		objComment.setMessage(input.nextLine());
		_comments.add(objComment);
	}
	
	public void viewComments() {
		for (Comment comment : _comments) {
			comment.display();
		}
	}
}


class InstructorPortal implements displayAssesment, MenuForClass{
	private StudentPortal studentPortal;
	private DiscussionForum discussionForum;
	private int _nOfAssesments;
	private Date dNow;
	private Instructor _instructor;
	private Scanner input;
	private SimpleDateFormat fmt;
	private ArrayList<Instructor> _instructors = new ArrayList<>();
	private ArrayList<LectureSlide> _lectureSlide = new ArrayList<>();
	private ArrayList<LectureVideo> _lectureVideo = new ArrayList<>();
	private ArrayList<AssesmentAssignment> _assesmentAssignment = new ArrayList<>();
	private ArrayList<AssesmentQuiz> _assesmentQuiz = new ArrayList<>();
	
	
	public InstructorPortal(Scanner input, Date dNow, SimpleDateFormat fmt, DiscussionForum discussionForum) {
		this.input = input;
		this.dNow = dNow;
		this.fmt = fmt;
		this.discussionForum = discussionForum;
		addInstructors();
	}
	
	public void initializeStudentPortal(StudentPortal studentPortal) {
		 this.studentPortal = studentPortal;
		 
	}
	
	private void instructors() {          // prints the instructors present in the instructor portal class
		System.out.println("Instructors:");
		int i = 0;
		for (Instructor obj : _instructors) {
			System.out.println(i + "->" + obj.getName());
			i += 1;
		}
		int response;
		System.out.print("choose id:");
		response = input.nextInt();
		_instructor = _instructors.get(response);
	}
	
	@Override
	public void menu() {       // serves queries
		instructors();
		int response;
		while(true) {
			System.out.println("Welcome " + _instructor.getName());
			System.out.print("1 Add Class Material\n2 Add Assesments\n3 View lecture materials\n4 View assessments\n5 Grade assessments\n6 Close assessment\n7 View comments\n8 Add comments\n9 Logout\n");
			response = input.nextInt();
			if(response==1) {
				_instructor.doSomething2classMaterial();
			}else if(response == 2) {
				_nOfAssesments += 1;
				_instructor.doSomething2Assesments();
			}else if(response == 3) {
				displayList("LectureSlide", false);
				displayList("LectureVideo", false);				
			}else if(response == 4) {
				displayList("Assesment", false);
			}else if(response == 5) {
				_instructor.performAction1_OnAssesment();
			}else if(response == 6) {
				_instructor.performAction2_OnAssesment();
			}else if(response == 8) {
				_instructor.addComment(discussionForum);
			}else if(response == 7) {
				_instructor.viewComments(discussionForum);
			}else if(response == 9){
				break;
			}else {
				System.out.println("Wrong code Entered, please enter again");
			}
		}
	}
	
	private void addInstructors() {   // initializes instructors for the instructor portal class 
		Instructor obj1 = new Instructor(0, input, fmt, dNow, this);
		_instructors.add(obj1);
		Instructor obj2 = new Instructor(1, input, fmt, dNow, this);
		_instructors.add(obj2);
	}
	
	public void insertInList(String nameOfList, Object obj) {    // insert the elements in the array list
		if(nameOfList.equals("Instructor")) {
			_instructors.add((Instructor)obj);
		}else if(nameOfList.equals("LectureSlide")){
			_lectureSlide.add((LectureSlide)obj);
		}else if(nameOfList.equals("LectureVideo")) {
			_lectureVideo.add((LectureVideo)obj);
		}else if(nameOfList.equals("AssesmentAssignment")) {
			_assesmentAssignment.add((AssesmentAssignment)obj);
		}else if(nameOfList.equals("AssesmentQuiz")) {
			_assesmentQuiz.add((AssesmentQuiz)obj);
		}
	}
	

	public void displayList(String nameOfList, boolean flag){      // prints the content of the array list in the instructor class
		boolean flag2 = false;
	    if(nameOfList.equals("LectureSlide")){
			for (LectureSlide objLectureSlide : _lectureSlide) {
				System.out.println();
				objLectureSlide.display();
			}
		}else if(nameOfList.equals("LectureVideo")) {
			for (LectureVideo objLectureVideo : _lectureVideo) {
				System.out.println();
				objLectureVideo.display();
			}
		}else if(nameOfList.equals("Assesment")) {
			int i = 0;
			int k = 0;
			int l = 0;
			while(i<_nOfAssesments) {
				if(k<_assesmentAssignment.size() && _assesmentAssignment.size()!=0  && _assesmentAssignment.get(k).getId() == i) {
					if (flag) {
						if( _assesmentAssignment.get(k).getStatus().equals("OPEN")) {
							flag2 = true;
							_assesmentAssignment.get(k).display();
						}
					}else {
						_assesmentAssignment.get(k).display();
					}
					k += 1;
				}else if(l<_assesmentQuiz.size() && _assesmentQuiz.size()!=0 && _assesmentQuiz.get(l).getId() == i) {
					if(flag) {
						if( _assesmentQuiz.get(l).getStatus().equals("OPEN")) {
							flag2 = true;
							_assesmentQuiz.get(l).display();
						}
					}else {
						_assesmentQuiz.get(l).display();
					}		
					l += 1;
				}
				i += 1;
			}
		}
	    if(flag) {
	    	_instructor.setBooleanFlag(flag2);
	    }
	    
	}
	
	public int getAssesmentNo() {
		return _nOfAssesments;
	}
	
	public StudentPortal getObjectStudentPortal(String name) {
		if(name.equals("StudentPortal")) {
			return studentPortal;
		}
		return null;
	}
	
	public AssesmentAssignment getObjectAssignment(int id) {
		for (AssesmentAssignment assesmentAssignment : _assesmentAssignment) {
			if (assesmentAssignment.getId() == id) {
				return assesmentAssignment;
			}
		}
		return null;
	}
	
	public AssesmentQuiz getObjectQuiz(int id) {
		for (AssesmentQuiz assesmentQuiz : _assesmentQuiz) {
			if(assesmentQuiz.getId() == id) {
				return assesmentQuiz;
			}
		}
		return null;
	}
	
	public int getSizeOfAssignment() {
		return _assesmentAssignment.size();
	}
	
	public int getSizeOfQuiz() {
		return _assesmentQuiz.size();
	}
	
	public String whichAssesment(int id) {     // finds what is the type of assessment(quiz or assignment) being selected by the user
		String id_typeAss = "";
		for (AssesmentAssignment assesmentAssignment : _assesmentAssignment) {
			if(assesmentAssignment.getId() == id) {
				id_typeAss = String.valueOf(id) + " Assignment";
				return id_typeAss;
			}
		}	
		for (AssesmentQuiz assesmentQuiz : _assesmentQuiz) {
			if(assesmentQuiz.getId() == id) {
				id_typeAss = String.valueOf(id) + " Quiz";
				return id_typeAss;
			}
		}
		return id_typeAss;
	}
	
	@Override
	public void gradeRelatedDisplay(String parameter) {    // prints the students with ungraded submissions for a particular assignment
		String arr[];
		boolean flag = false;
		AssesmentQuiz assesmentQuiz = null;
		AssesmentAssignment assesmentAssignment = null;
		arr = parameter.split("\\s");
		if(arr[1].equals("Assignment")) {
			assesmentAssignment = this.getObjectAssignment(Integer.parseInt(arr[0]));
			int temp = this.studentPortal.getSizeOfListStudent();
			for (int i = 0; i < temp; i++) {
				if(assesmentAssignment.seekSubmissions(i)=="1" && assesmentAssignment.getStatus().equals("OPEN")) {
					System.out.println(i + " "+ this.studentPortal.getAnyStudent(i).getName());
					flag = true;
				}
			}
		}else {
			assesmentQuiz = this.getObjectQuiz(Integer.parseInt(arr[0]));
			int temp = this.studentPortal.getSizeOfListStudent();
			for (int i = 0; i < temp; i++) {
				if(assesmentQuiz.seekSubmissions(i)=="1" && assesmentQuiz.getStatus().equals("OPEN")) {
					System.out.println(i + " "+ this.studentPortal.getAnyStudent(i).getName());
					flag = true;
				}
			}			
		}
		_instructor.setBooleanFlag(flag);
	}
	
	@Override
	public void actOnOpenAssesments() {     // prints the assignments which are "OPENED" status
		boolean flag = false;
		int i = 0;
		int k = 0;
		int l = 0;
		while(i<_nOfAssesments) {
			if(k<_assesmentAssignment.size() && _assesmentAssignment.size()!=0  && _assesmentAssignment.get(k).getId() == i) {
				if(_assesmentAssignment.get(k).getStatus().equals("OPEN")) {
					flag = true;
					_assesmentAssignment.get(k).display();
				}
				k += 1;
			}else if(l<_assesmentQuiz.size() && _assesmentQuiz.size()!=0 && _assesmentQuiz.get(l).getId() == i) {
				if(_assesmentQuiz.get(l).getStatus().equals("OPEN")) {	
					flag = true;
					_assesmentQuiz.get(l).display();
				}
				l += 1;
			}
			i += 1;
		}
		_instructor.setBooleanFlag(flag);
	}	
	
}

class Instructor implements ActionToClassMaterial, Assesments{    // class for instructor
	private String _name;
	private Scanner input;
	private SimpleDateFormat fmt;
	private Date dNow;
	private InstructorPortal instructorPortal;
	private boolean flag = true;
	
	public Instructor(int i, Scanner input, SimpleDateFormat fmt, Date dNow, InstructorPortal instructorPortal) {
		_name = "I" + String.valueOf(i);
		this.input = input;
		this.dNow = dNow;
		this.fmt = fmt;
		this.instructorPortal = instructorPortal;
	}
	
	@Override
	public void doSomething2classMaterial() {       // adds the lecture material
		int response;
		while(true) {
			System.out.print("1 Add Lecture Slide\n2 Add Lecture Video\n");
			response = input.nextInt();
			if(response == 1) {
				LectureSlide obj = new LectureSlide(input, _name, fmt, dNow);
				obj.upload();
				instructorPortal.insertInList("LectureSlide", obj);
				break;
			}else if(response == 2){
				LectureVideo obj = new LectureVideo(input, _name, fmt, dNow);
				obj.upload();
				instructorPortal.insertInList("LectureVideo", obj);
				break;
			}else {
				System.out.println("wrong code entered, Enter again");
			}
		}
	}
	
	@Override
	public void doSomething2Assesments() {        // uploads the assessments
		int response;
		while(true) {
			System.out.print("1 Add Assignment\n2 Add Quiz\n");
			response = input.nextInt();
			if(response == 1) {
				AssesmentAssignment obj = new AssesmentAssignment(input, instructorPortal.getAssesmentNo()-1, instructorPortal.getObjectStudentPortal("StudentPortal").getSizeOfListStudent());
				obj.formAssessment();
				instructorPortal.insertInList("AssesmentAssignment", obj);
				break;
			}else if(response == 2) {
				AssesmentQuiz obj = new AssesmentQuiz(input, instructorPortal.getAssesmentNo()-1, instructorPortal.getObjectStudentPortal("StudentPortal").getSizeOfListStudent());
				obj.formAssessment();
				instructorPortal.insertInList("AssesmentQuiz", obj);				
				break;
			}else {
				System.out.println("wrong code entered, Enter again");
			}
		}	
	}

       
	@Override
	public void performAction1_OnAssesment() {       // grade the assessment
		int response1;
		String arr[];
		AssesmentAssignment assesmentAssignment = null;
		AssesmentQuiz assesmentQuiz = null;
		System.out.println("List of Assesments:");
		instructorPortal.displayList("Assesment", true);
		if((instructorPortal.getSizeOfQuiz()==0 && instructorPortal.getSizeOfAssignment()==0 )|| !flag) {
			System.out.println("There is no assesment to be graded!");
		}else {

			while(true) {
				System.out.print("Enter ID of Assignment to view submissions:");
				response1 = input.nextInt();
				arr = instructorPortal.whichAssesment(response1).split("\\s");
				if(arr.length<2) {
					System.out.println("Entered wrong id of the assignment!");
					continue;
				}else {
					break;
				}
			}
			if(arr[1].equals("Assignment")) {
				assesmentAssignment = instructorPortal.getObjectAssignment(Integer.parseInt(arr[0]));
			}else {
				assesmentQuiz = instructorPortal.getObjectQuiz(Integer.parseInt(arr[0]));
			}
			int response2 = 0;
			while(true) {
				System.out.println("Choose ID from these Ungraded Submissions:");
				instructorPortal.gradeRelatedDisplay(arr[0]+" "+arr[1]);
				if(!flag) {
					break;
				}
				response2 = input.nextInt();
				if(response2>instructorPortal.getObjectStudentPortal("StudentPortal").getSizeOfListStudent() || response2<0) {
					System.out.println("wrong code entered, enter again");
					continue;
				}else {
					break;
				}
			}
			if(!flag) {
				System.out.println("There are no submissions  for this assesment at the moment");
			}else {
				if(arr[1].equals("Assignment")) {
					assesmentAssignment.evaluate(response2, _name);
				}else {
					assesmentQuiz.evaluate(response2, _name);
				}
			}
		}
	}
	
	@Override
	public void performAction2_OnAssesment() {       // close the assessment
		int response;
		String arr[];
		instructorPortal.actOnOpenAssesments();
		if(flag) {
			while(true) {
				System.out.print("Enter id of assignment to close: ");
				response = input.nextInt();
				arr = instructorPortal.whichAssesment(response).split("\\s");
				if(arr.length<2) {
					System.out.println("Wrong Code entered, enter again!");
				}else {
					break;
				}
			}
			if(arr[1].equals("Assignment")) {
				instructorPortal.getObjectAssignment(Integer.parseInt(arr[0])).changeStatus();;
			}else {
				instructorPortal.getObjectQuiz(Integer.parseInt(arr[0])).ChangeStatus();
			}
		}else {
			System.out.println("there is no assesment to close!");
		}
	}
	
	public void addComment(DiscussionForum discussionForum) {
		discussionForum.addComment(_name);
	}
	
	public void viewComments(DiscussionForum discussionForum) {
		discussionForum.viewComments();
	}
	
	public String getName() {
		return _name;
	}
	
	public void setBooleanFlag(boolean flag) {
		this.flag = flag;
	}
	
}


class StudentPortal implements displayAssesment, MenuForClass{
	private DiscussionForum discussionForum;
	private InstructorPortal instructorPortal;
	private int _nOfStudent;
	private Scanner input;
	private Student _student;
	private ArrayList<Student> _students = new ArrayList<>();
	
	public StudentPortal(Scanner input, DiscussionForum discussionForum) {
		this.input = input;
		this.discussionForum = discussionForum;
		addStudents();
	}
	
	public void initializeInstructorPortal(InstructorPortal instructorPortal) {
		this.instructorPortal = instructorPortal;
	}
	
	private void addStudents() {        
		_nOfStudent += 1;
		Student obj1 = new Student(_nOfStudent-1, input, this);
		_students.add(obj1);
		_nOfStudent += 1;
		Student obj2 = new Student(_nOfStudent-1, input, this);
		_students.add(obj2);
		_nOfStudent += 1;
		Student obj3 = new Student(_nOfStudent-1, input, this);
		_students.add(obj3);		
	}
	
	public void students() {
		System.out.println("Students:");
		int i = 0;
		for (Student obj : _students) {
			System.out.println(i + "->" + obj.getName());
			i += 1;
		}
		int response;
		System.out.print("choose id:");
		response = input.nextInt();
		_student = _students.get(response);
	}
	
	@Override
	public void menu() {
		students();
		int response;
		while(true) {
			System.out.println("Welcome " + _student.getName());
			System.out.print("1 View lecture materials\n2 View assessments\n3 Submit assessment\n4 View grades\n5 View comments\n6 Add comments\n7 Logout\n");
			response = input.nextInt();
			if(response==1) {
				_student.doSomething2classMaterial();
			}else if(response == 2) {
				_student.doSomething2Assesments();
			}else if(response == 3) {
				_student.performAction1_OnAssesment();
			}else if(response == 4) {
				_student.performAction2_OnAssesment();
			}else if(response == 6) {
				_student.addComment(discussionForum);
			}else if(response == 5) {
				_student.viewComments(discussionForum);
			}else if(response == 7){
				break;
			}else {
				System.out.println("Wrong code Entered, please enter again");
			}
		}
	}
	
	public void displayListCall(String nameOfList, boolean flag) {
		instructorPortal.displayList(nameOfList, flag);
	}
	
	public int getSizeOfListStudent() {
		return _students.size();
	}
	
	public InstructorPortal getInstructorPortal() {
		return instructorPortal;
	}
	
	public Student getStudent() {
		return _student;
	}
	
	public Student getAnyStudent(int index) {
		return _students.get(index);
	}
	
	@Override
	public void gradeRelatedDisplay(String parameter) {       // prints the assessments which are not submitted by a particular student out of all opened assessments
		boolean flag = false;
		int _nOfAssesments = instructorPortal.getAssesmentNo();
		AssesmentAssignment assesmentAssignment = null;
		AssesmentQuiz assesmentQuiz = null;
		for (int i = 0; i < _nOfAssesments; i++) {
			assesmentAssignment = instructorPortal.getObjectAssignment(i);
			if(assesmentAssignment == null) {
				assesmentQuiz = instructorPortal.getObjectQuiz(i);
				if(assesmentQuiz.seekSubmissions(_student.getId()) == null && assesmentQuiz.getStatus().equals("OPEN")) {
					assesmentQuiz.display();
					flag = true;
				}				
			}else {
				if(assesmentAssignment.seekSubmissions(_student.getId()) == null && assesmentAssignment.getStatus().equals("OPEN")) {
					assesmentAssignment.display();
					flag = true;
				}
			}
		}
		_student.flag = flag;
	}
	
	@Override
	public void actOnOpenAssesments() {        // Segregates and prints the assessments of a particular student on the basis of graded or not 
		int _nOfAssesments = instructorPortal.getAssesmentNo();
		AssesmentAssignment assesmentAssignment;
		AssesmentQuiz assesmentQuiz;
		String graded = "";
		String gradedHelp = "";
		String ungradedHelp = "";
		String ungraded = "";
		for (int i = 0; i < _nOfAssesments; i++) {
			assesmentAssignment = instructorPortal.getObjectAssignment(i);
			if(assesmentAssignment == null) {
				assesmentQuiz = instructorPortal.getObjectQuiz(i);
				if(assesmentQuiz.seekSubmissions(_student.getId())=="2") {
					graded += String.valueOf(i) + " ";
					gradedHelp += "q" + " ";
				}else if(assesmentQuiz.seekSubmissions(_student.getId())=="1") {
					ungraded +=  String.valueOf(i) + " " ;
					ungradedHelp +=  "q" + " ";
				}
			}else {
				
				if(assesmentAssignment.seekSubmissions(_student.getId())=="2") {
					graded += String.valueOf(i) + " ";
					gradedHelp +=  "a" + " ";
				}else if(assesmentAssignment.seekSubmissions(_student.getId())=="1") {
					ungraded +=  String.valueOf(i) + " " ;
					ungradedHelp += "a" + " ";
				}
			}
		}
		String _graded[] = graded.split("\\s");
		String _gradedHelp[] = gradedHelp.split("\\s");
		String _ungraded[] = ungraded.split("\\s");
		String _ungradedHelp[] = ungradedHelp.split("\\s");
		int j = 0;
		System.out.println("Graded Submissions:");
		if(_graded[0] == "") {
			System.out.println();
		}else {
			for (String string : _graded) {
				if(_gradedHelp[j].equals("a")) {
					instructorPortal.getObjectAssignment(Integer.parseInt(string)).displayGraded(_student.getId());
				}else {
					instructorPortal.getObjectQuiz(Integer.parseInt(string)).displayGraded(_student.getId());
				}
				j += 1;
			}
		}
		j = 0;
		System.out.println("UnGraded Submissions:");
		if(_ungraded[0] == "") {
			System.out.println();
		}else {
			for (String string : _ungraded) {
				if(_ungradedHelp[j].equals("a")) {
					instructorPortal.getObjectAssignment(Integer.parseInt(string)).displayUnGraded(_student.getId());
				}else {
					instructorPortal.getObjectQuiz(Integer.parseInt(string)).displayUnGraded(_student.getId());
				}
				j += 1;
			}
		}
	}
}


class Student implements ActionToClassMaterial, Assesments{
	private String _name;
	private int _id;
	private StudentPortal studentPortal;
	private Scanner input;
	boolean flag = true;
	
	public Student(int i, Scanner input, StudentPortal studentPortal){
		_id = i;
		this.input = input;
		_name = "S" + String.valueOf(i);
		this.studentPortal = studentPortal;
	}
	
	@Override
	public void doSomething2classMaterial() {
		studentPortal.displayListCall("LectureSlide", false);
		studentPortal.displayListCall("LectureVideo", false);
	}
	
	@Override
	public void doSomething2Assesments() {
		studentPortal.displayListCall("Assesment", false);
	}
	
	@Override
	public void performAction1_OnAssesment() {        // submit the assessment
		String arr[];	
		System.out.println("Pending assessments:");
		studentPortal.gradeRelatedDisplay(String.valueOf(_id));
		int response;
		if(flag) {
			System.out.print("Enter ID of Assessment:");
			while(true) {
				response = input.nextInt();
				arr = studentPortal.getInstructorPortal().whichAssesment(response).split("\\s");
				if(arr.length<2) {
					System.out.println("Entered wrong id of the assignment");
					continue;
				}else {
					break;
				}
			}
			if(arr[1].equals("Assignment")) {
				studentPortal.getInstructorPortal().getObjectAssignment(Integer.parseInt(arr[0])).makeSubmission(_id);
			}else {
				studentPortal.getInstructorPortal().getObjectQuiz(Integer.parseInt(arr[0])).makeSubmission(_id);
			}
		}else {
			System.out.println("WOW, No pending assessments!");
		}
	}
	
	@Override
	public void performAction2_OnAssesment() {        // view the grading of the various submissions
		studentPortal.actOnOpenAssesments();
	}
	
	public void addComment(DiscussionForum discussionForum) {
		discussionForum.addComment(_name);
	}
	
	public void viewComments(DiscussionForum discussionForum) {
		discussionForum.viewComments();
	}
	
	public String getName() {
		return _name;
	}
	
	public int getId() {
		return _id;
	}
}

class AssesmentQuiz implements AssessmentRelated{
	private String _quizQuestion;
	private int _maxMarks = 1;
	private String _status;
	private int _id;
	private String _submissions[][];   
	private float _marksObtained[];
	private Scanner input;
	
	public AssesmentQuiz(Scanner input, int _id, int _nOfStudents) {
		this.input = input;
		_status = "OPEN";
		this._id = _id;
		_submissions = new String[3][_nOfStudents];
		_marksObtained = new float[_nOfStudents];
	}
	
	@Override
	public void formAssessment() {
		System.out.print("Enter the Quiz Question:");
		input.nextLine();
		_quizQuestion = input.nextLine();
	}
	
	@Override
	public void evaluate(int studentId, String gradeeName) {
		_submissions[2][studentId] = gradeeName;
		System.out.print("Submission(Quiz): "+ _submissions[1][studentId]+ "\n---------------\nMax Marks: "+ _maxMarks + "\nMarks Scored: ");
		while(true) {
			_marksObtained[studentId] = input.nextFloat();
			if(_marksObtained[studentId]>_maxMarks || _marksObtained[studentId]<0) {
				System.out.print("Error in marks Entered!\nPlease enter again:");
				continue;
			}else {
				_submissions[0][studentId] = "2";
				break;
			}
		}
	}
	
	@Override
	public void makeSubmission(int indexStudId) {
		if(_status.equals("CLOSED")) {
			System.out.println("The assignment has been closed");
		}else {
			if(_submissions[0][indexStudId]!=null) {
				System.out.println("student has submitted the assignment!");
			}else {
				System.out.print(_quizQuestion + ": ");
				_submissions[0][indexStudId] = "1";
				input.nextLine();
				_submissions[1][indexStudId] = input.next();
			}
		}
	}
	
	public String seekSubmissions(int index) {
		return _submissions[0][index];
	}
	
	@Override
	public void display() {
		System.out.println("ID: "+_id + ", Question: "+_quizQuestion + "\n-------------------");
	}

	@Override
	public void displayGraded(int studentID) {
		System.out.println("Submission(Quiz): " + _submissions[1][studentID]);
		System.out.println("marks obtained: "+ _marksObtained[studentID]);
		System.out.println("Graded By: " + _submissions[2][studentID]);
	}
	
	public void displayUnGraded(int studentID) {
		System.out.println("Submission(Quiz): " + _submissions[1][studentID]);
	}
	public String getStatus() {
		return _status;
	}
	
	public void ChangeStatus() {
		_status = "CLOSED";
	}
	public int getId() {
		return _id;
	}
	
}

class AssesmentAssignment implements AssessmentRelated{
	private String _probStatement;
	private String _status;
	private int _maxMarks;
	private int _id;
	private String _submissions[][];   
	private int _marksObtained[];
	
	
	Scanner input;
	
	public AssesmentAssignment(Scanner input, int _id, int _nOfStudents) {
		this.input = input;
		_status = "OPEN";
		this._id = _id;
		_submissions = new String[3][_nOfStudents]; 
		_marksObtained = new int[_nOfStudents];
	}
	
	@Override
	public void formAssessment() {
		System.out.print("Enter the Problem Statement:");
		input.nextLine();
		_probStatement = input.nextLine();
		System.out.print("Enter the Maximum marks:");
		_maxMarks = input.nextInt();
	}
	
	@Override
	public void display() {
		System.out.println("ID: " + _id + ", Assignment: "+ _probStatement + ", Max marks: "+ _maxMarks + "\n------------------");
	}
	
	public int getId() {
		return _id;
	}
	
	@Override
	public void displayGraded(int studentID) {
		System.out.println("Submission: " + _submissions[1][studentID]);
		System.out.println("Marks Scored: " + _marksObtained[studentID]);
		System.out.println("Graded By: " + _submissions[2][studentID]);
	}
	
	public void displayUnGraded(int studentID) {
		System.out.println("Submission: " + _submissions[1][studentID]);
	}
	
	@Override
	public void makeSubmission(int indexStudId) {
		if(_status.equals("CLOSED")) {
			System.out.println("The assignment has been closed");
		}else {
			if(_submissions[0][indexStudId]!=null) {
				System.out.println("dent has submitted the assignment!");
			}else {
				String temp2;
				String temp[];
				input.nextLine();
				while(true) {
					System.out.print("Enter the filename of the assignment: ");
					temp2 = input.nextLine();
					temp = temp2.split("\\.");
					if(temp.length==2) {
						if(temp[1].equals("zip")) {
							_submissions[0][indexStudId] = "1";
							_submissions[1][indexStudId] = temp2;
							break;
						}else {
							System.out.println("Wrong submission format!");
						}
					}else {
						System.out.println("Wrong submission format!");
					}
				}
			}
		}
	}
	
	@Override
	public void evaluate(int studentId, String gradeeName) {
		_submissions[2][studentId] = gradeeName;
		System.out.print("Submission: "+ _submissions[1][studentId]+ "\n---------------\nMax Marks: "+ _maxMarks + "\nMarks Scored: ");
		_marksObtained[studentId] = input.nextInt();
		while(true) {
			if(_marksObtained[studentId]>_maxMarks || _marksObtained[studentId]<0) {
				System.out.println("Error in marks Entered, please enter again");
				continue;
			}else {
				_submissions[0][studentId] = "2";
				break;
			}
		}
	}
	
	public String getStatus() {
		return _status;
	}
	
	public void changeStatus() {
		_status = "CLOSED";
	}
	
	public String seekSubmissions(int index) {
		return _submissions[0][index];
	}
}

class LectureSlide implements ClassMaterial{
	private String _topic;
	private int _nOfSlides;
	private String _slides[];
	private Scanner input;
	private String _uploadedBy;
	private String _dateOfUpload;
	
	
	public LectureSlide(Scanner input, String uploadee, SimpleDateFormat fmt, Date dNow) {
		dNow = new Date();
		_dateOfUpload = fmt.format(dNow);
		this.input = input;	
		_uploadedBy = uploadee;
	}
	
	@Override
	public void upload() {
		input.nextLine();
		System.out.print("Enter the topic of Slides:");
		_topic = input.nextLine();
		System.out.print("Enter no of Slides:");
		_nOfSlides = input.nextInt();
		input.nextLine();
		System.out.println("Enter Content of Slides");
		_slides = new String[_nOfSlides];
		for (int i = 0; i < _nOfSlides; i++) {
			System.out.print("Content of Slide " + (i+1) + ":");
			_slides[i] = input.nextLine();
		}
	}
	
	@Override
	public void display() {
		System.out.println("Title :" + _topic);
		for (int i = 0; i < _slides.length; i++) {
			System.out.println("Slide "+ (i+1) +": " + _slides[i]);
		}
		System.out.println("Number of Slides :"+_nOfSlides);
		System.out.println("Date of Upload :"+ _dateOfUpload);
		System.out.println("Uploaded by: " + _uploadedBy);
	}
}

class LectureVideo implements ClassMaterial{
	private String _topic;
	private String _fileName;
	private Scanner input;
	private String _uploadedBy;
	private String _dateOfUpload;
	
	public LectureVideo(Scanner input, String uploadee, SimpleDateFormat fmt, Date dNow) {
		this.input = input;
		dNow = new Date();
		_dateOfUpload = fmt.format(dNow);
		_uploadedBy = uploadee;
	}
	
	@Override
	public void upload() {
		String temp[];
		input.nextLine();
		System.out.print("Enter the topic of Video: ");
		_topic = input.nextLine();
		
		while(true) {
			System.out.print("Enter the filename of video: ");
			_fileName = input.nextLine();
			temp = _fileName.split("\\.");
			if(temp.length==2) {
				if(temp[1].equals("mp4")) {
					break;
				}else {
					System.out.println("Wrong submission format!");
				}
			}else {
				System.out.println("Wrong submission format!");
			}
		}
	}
	
	@Override
	public void display() {
		System.out.println("Title of video: " + _topic);
		System.out.println("Video File: " + _fileName);
		System.out.println("Date of Upload: " + _dateOfUpload);
		System.out.println("Uploaded by: " + _uploadedBy);
		System.out.println("");
	}
	
}

interface displayAssesment{
	public void gradeRelatedDisplay(String paramter);
	public void actOnOpenAssesments();
}

interface ActionToClassMaterial{
	public void doSomething2classMaterial();
}

interface Assesments{
	public void doSomething2Assesments();
	public void performAction1_OnAssesment();
	public void performAction2_OnAssesment();
}

interface ClassMaterial{
	public void upload();
	public void display();
}

interface MenuForClass{
	public void menu();
}

interface AssessmentRelated{
	public void formAssessment();
	public void makeSubmission(int studId);
	public void evaluate(int studentId, String gradeeName);
	public void display();
	public void displayGraded(int studentId);
}