import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class aluno extends pessoa
{
    String name; 
    String telephone_number; 
    String birthdate;
    String register_date;
    String last_edit;
    float final_grade;
    LocalDateTime ldt = LocalDateTime.now();
    
    // Variables for dates
    int day;
    int month;
    int year;
    
    //Exception handling
    String birth_date_holder;
    boolean exception;
    
    public aluno(String name, String telephone_number, String birthdate, String register_date, String last_edit, float final_grade)
    {    
        super(name, telephone_number, birthdate, register_date, last_edit, final_grade);
        this.name = name;
        this.telephone_number = telephone_number;
        this.birthdate = birthdate;
        this.register_date = register_date;
        this.last_edit = last_edit;
        this.final_grade = final_grade;
    }
    
    @Override
    public boolean valid_phonenumber(String phone_number)
    {
        if(phone_number.length() < 10)
        {
            return false;
        }
        if(phone_number.length() > 16)
        {
            return false;
        }
        return true;
    }
    
    @Override
    public void set_name(String new_name)
    {
        this.name = new_name;
        String last_edit_str = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ldt);
        this.last_edit = last_edit_str;
    }
    
    @Override
    public void set_phone(String new_phone)
    {
        if(valid_phonenumber(new_phone) == true)
        {
            this.telephone_number = new_phone;
            String last_edit_str = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ldt);
            this.last_edit = last_edit_str;
        }
        else
        {
            System.out.print("\n\n*Número de telefone inválido*\n\n");
        }
    }
    
    @Override
    public void set_birthdate(String new_birth)
    {
        try
        {
            String[] birth_buffer = new_birth.split("/");
            day = Integer.parseInt(birth_buffer[0]);
            month = Integer.parseInt(birth_buffer[1]);
            year = Integer.parseInt(birth_buffer[2]);
            exception = false;
        }
        catch(Exception e)
        {
            exception = true;
        }
        
        if(exception == false)
        {
            this.birthdate = new_birth;
            String last_edit_str = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ldt);
            this.last_edit = last_edit_str;
        }
        else
        {
            System.out.print("\n\n*Data de nascimento inválida*\n\n");
        }
    }
    
    @Override
    public void set_grade(String new_grade)
    {
        exception = true;
        try
        {
            Float.parseFloat(new_grade);
            exception = false;
        }
        catch(Exception e)
        {
            exception = true;
        }
        
        if(exception == false)
        {
            this.final_grade = Float.parseFloat(new_grade);
            String last_edit_str = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ldt);
            this.last_edit = last_edit_str;
        }
        else
        {
            System.out.print("\n\n*Nota inválida, ela deve ser um número*\n\n");
        }
    }
}
