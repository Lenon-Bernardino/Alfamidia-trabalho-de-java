import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class main
{
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
    
    public main()
    {
        Scanner sc=new Scanner(System.in);
        
        String birth_date_holder;
            
        String name; 
        String telephone_number = ""; 
        float final_grade;
            
        // Variables for dates
        int day;
        int month;
        int year;
        String birthdate = "";
        String last_edit = "";
        
        // variable for exception handling
        String message;
        boolean exception = true;
        
        // variable for looking up usernames
        String username_lookup;
        boolean found_user = false;
        List<pessoa> pessoas = new ArrayList<pessoa>();
        List<aluno> alunos = new ArrayList<aluno>();
        
        
        // people for testing
        pessoa person1 = new pessoa("John doe", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", -1);
        pessoa person2 = new pessoa("Admin", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", -1);
        pessoas.add(person1);
        pessoas.add(person2);
        
        // students for testing
        aluno student1 = new aluno("John doe (student)", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", 45);
        aluno student2 = new aluno("Admin (student)", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", 90);
        alunos.add(student1);
        alunos.add(student2);
        
        // secondary commands
        String command2 = "";
        
        while(true)
        {
            LocalDateTime ldt = LocalDateTime.now();
            System.out.print("\n============================================================\n");
            System.out.print("Escreva 'criar' para criar uma pessoa ou aluno\n");
            System.out.print("Escreva 'ver' para mostrar alunos e pessoas\n");
            System.out.print("Escreva 'editar' para mudar os dados de uma pessoa ou aluno\n");
            System.out.print("Escreva 'deletar' para deletar uma pessoa ou aluno\n");
            System.out.print("Escreva '0' para sair\n\n");
            System.out.print("Comando: ");
            String command = sc.nextLine();
            
            if(command.equals("0"))
            {
                break;
            }
            
            if(command.equals("criar")) 
            {
                System.out.flush();
                System.out.print("Informe o nome da pessoa: ");
                name = sc.nextLine();
                
                message = "Informe o número de telefone da pessoa: ";
                while(valid_phonenumber(telephone_number) == false)
                {
                    System.out.flush();
                    System.out.print(message);
                    telephone_number = sc.nextLine();
                    message = "Informe um número de telefone válido: ";
                }

                message = "Informe a data de nascimento da pessoa (no formato dia/mes/ano): ";
                while(exception == true)
                {
                    try
                    {
                        System.out.flush();
                        System.out.print(message);
                        birth_date_holder = sc.nextLine();
                        String[] birth_buffer = birth_date_holder.split("/");
                        day = Integer.parseInt(birth_buffer[0]);
                        month = Integer.parseInt(birth_buffer[1]);
                        year = Integer.parseInt(birth_buffer[2]);
                        birthdate = birth_date_holder;
                        exception = false;
                    }
                    catch(Exception e)
                    {
                        exception = true;
                        message = "Informe uma data de nascimento válida (no formato dia/mes/ano): ";
                    }
                }
                
                // Defining the register date, which is the same as the last edit, since registering is considered editing.
                String last_edit_str = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ldt);
                last_edit = last_edit_str;
                
                System.out.print("\nDeseja informar a nota final do curso? (s/n): ");
                String resposta = sc.nextLine();
                if(resposta.equals("s")) // Creates a student if they tell the grade
                {
                    message = "\nInforme a nota final do aluno: \n";
                    exception = true;
                    
                    while(exception == true)
                    {
                        try 
                        {
                            System.out.flush();
                            System.out.print(message);
                            final_grade = Float.parseFloat(sc.nextLine());
                            aluno student = new aluno(name, telephone_number, birthdate, last_edit, last_edit, final_grade);
                            alunos.add(student);
                            exception = false;
                        }
                        catch(Exception e)
                        {
                            exception = true;
                            message = "\nInforme uma nota final válida: \n";
                        }
                    }
                }
                else // Creates a person if they don't want to tell the grade, and defines it as -1.
                {
                    pessoa person = new pessoa(name, telephone_number, birthdate, last_edit, last_edit, -1);
                    pessoas.add(person);
                }
            }
            
            if(command.equals("ver")) 
            {
                System.out.flush();
                System.out.print("Pessoas:\n\n");
                
                for(int i = 0; i < pessoas.size(); i++) // Iterate through people
                {
                    System.out.print("Nome: " + pessoas.get(i).name + "\n");
                    System.out.print("Telefone: " + pessoas.get(i).telephone_number + "\n");
                    System.out.print("Data de nascimento: " + pessoas.get(i).birthdate + "\n");
                    System.out.print("Data da última edição: " + pessoas.get(i).register_date + "\n\n");
                }
                
                System.out.print("Alunos:\n\n");
                
                for(int i = 0; i < alunos.size(); i++) // Iterate through students
                {
                    System.out.print("Nome: " + alunos.get(i).name + "\n");
                    System.out.print("Telefone: " + alunos.get(i).telephone_number + "\n");
                    System.out.print("Nota final: " + String.valueOf(alunos.get(i).final_grade)  + "\n");
                    System.out.print("Data de nascimento: " + alunos.get(i).birthdate + "\n");
                    System.out.print("Data da última edição: " + alunos.get(i).register_date + "\n\n");
                }
            }
            
            if(command.equals("editar")) // Edit student and people's data.
            {
                found_user = false;
                System.out.print("Escreva o nome do usuário que você deseja editar: ");
                username_lookup = sc.nextLine();
                
                for(int i = 0; i < pessoas.size(); i++) // Look for people with that name
                {
                    if(pessoas.get(i).name.equals(username_lookup)) 
                    {
                        found_user = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está editando o seguinte usuário (uma pessoa):\n");
                        System.out.print("Nome: " + pessoas.get(i).name + "\n");
                        System.out.print("Telefone: " + pessoas.get(i).telephone_number + "\n");
                        System.out.print("Data de nascimento: " + pessoas.get(i).birthdate + "\n");
                        System.out.print("Data da última edição: " + pessoas.get(i).register_date + "\n\n");
                        System.out.print("Para editar o nome, digite nome\n");
                        System.out.print("Para editar o telefone, digite telefone\n");
                        System.out.print("Para editar a data de nascimento, digite nascimento\n");
                        System.out.print("Para cancelar, digite 0\n");
                        command2 = sc.nextLine();
                        
                        if(command2.equals("0")) // In case that's not the person they wanna edit.
                        {
                            break;
                        }
                        
                        if(command2.equals("nome"))
                        {
                            /*
                            In this if statement I'm gonna use a lot of nested else statements
                            this is because if you wanted to change a user's name to "telephone"
                            then without the else statements, it would end up making you edit their phone number
                               */
                            System.out.print("\n\nEscreva o novo nome deste usuário: \n");
                            command2 = sc.nextLine();
                            pessoas.get(i).set_name(command2);
                        }
                        else
                        {
                            if(command2.equals("telefone"))
                            {
                                System.out.print("\n\nEscreva o novo telefone deste usuário: \n");
                                command2 = sc.nextLine();
                                pessoas.get(i).set_phone(command2);
                            }
                            else
                            {
                                if(command2.equals("nascimento"))
                                {
                                    System.out.print("\n\nEscreva a nova data de nascimento deste usuário: \n");
                                    command2 = sc.nextLine();
                                    pessoas.get(i).set_birthdate(command2);
                                }
                                else
                                {
                                    System.out.print("\n\nComando inválido, voltando ao início.\n");
                                }
                            }
                        }
                    }
                }
                
                for(int i = 0; i < alunos.size(); i++) // Look for STUDENTS with that name
                {
                    if(alunos.get(i).name.equals(username_lookup)) 
                    {
                        found_user = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está editando o seguinte usuário (um aluno):\n");
                        System.out.print("Nome: " + alunos.get(i).name + "\n");
                        System.out.print("Telefone: " + alunos.get(i).telephone_number + "\n");
                        System.out.print("Data de nascimento: " + alunos.get(i).birthdate + "\n");
                        System.out.print("Data da última edição: " + alunos.get(i).register_date + "\n\n");
                        System.out.print("Para editar o nome, digite nome\n");
                        System.out.print("Para editar o telefone, digite telefone\n");
                        System.out.print("Para editar a data de nascimento, digite nascimento\n");
                        System.out.print("Para editar a nota final, digite nota\n");
                        System.out.print("Para cancelar, digite 0\n");
                        command2 = sc.nextLine();
                        
                        if(command2.equals("0")) // In case that's not the person they wanna edit.
                        {
                            break;
                        }
                        
                        if(command2.equals("nome"))
                        {
                            /*
                            In this if statement I'm gonna use a lot of nested else statements
                            this is because if you wanted to change a user's name to "telephone"
                            then without the else statements, it would end up making you edit their phone number
                               */
                            System.out.print("\n\nEscreva o novo nome deste usuário: \n");
                            command2 = sc.nextLine();
                            alunos.get(i).set_name(command2);
                        }
                        else
                        {
                            if(command2.equals("telefone"))
                            {
                                System.out.print("\n\nEscreva o novo telefone deste usuário: \n");
                                command2 = sc.nextLine();
                                alunos.get(i).set_phone(command2);
                            }
                            else
                            {
                                if(command2.equals("nascimento"))
                                {
                                    System.out.print("\n\nEscreva a nova data de nascimento deste usuário: \n");
                                    command2 = sc.nextLine();
                                    alunos.get(i).set_birthdate(command2);
                                }
                                else
                                {
                                    if(command2.equals("nota"))
                                    {
                                        System.out.print("\n\nEscreva a nova nota final deste aluno: \n");
                                        command2 = sc.nextLine();
                                        alunos.get(i).set_grade(command2);
                                    }
                                    else
                                    {
                                        System.out.print("\n\nComando inválido, voltando ao início.\n");
                                    }
                                }
                            }
                        }
                    }
                }
                
                if(found_user == false)
                {
                    System.out.print("\n-----------------------\n");
                    System.out.print("Usuário não encontrado.");
                    System.out.print("\n-----------------------\n");
                }
            }
            
            if(command.equals("deletar")) // Edit student and people's data.
            {
                found_user = false;
                System.out.print("Escreva o nome do usuário que você deseja deletar: ");
                username_lookup = sc.nextLine();
                
                for(int i = 0; i < pessoas.size(); i++) // Look for people with that name
                {
                    if(pessoas.get(i).name.equals(username_lookup)) 
                    {
                        found_user = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está visualizando o seguinte usuário (uma pessoa):\n");
                        System.out.print("Nome: " + pessoas.get(i).name + "\n");
                        System.out.print("Telefone: " + pessoas.get(i).telephone_number + "\n");
                        System.out.print("Data de nascimento: " + pessoas.get(i).birthdate + "\n");
                        System.out.print("Data da última edição: " + pessoas.get(i).register_date + "\n\n");
                        System.out.print("\n\nVocê deseja deletar este usuário? (s/n)");
                        command2 = sc.nextLine();
                        if(command2.equals("s"))
                        {
                            pessoas.remove(i);
                        }
                        System.out.print("\nUsuário removido.\n");
                    }
                }
                
                for(int i = 0; i < alunos.size(); i++) // Look for STUDENTS with that name
                {
                    if(alunos.get(i).name.equals(username_lookup)) 
                    {
                        found_user = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está visualizando o seguinte usuário (um aluno):\n");
                        System.out.print("Nome: " + alunos.get(i).name + "\n");
                        System.out.print("Telefone: " + alunos.get(i).telephone_number + "\n");
                        System.out.print("Data de nascimento: " + alunos.get(i).birthdate + "\n");
                        System.out.print("Data da última edição: " + alunos.get(i).register_date + "\n\n");
                        System.out.print("\n\nVocê deseja deletar este usuário? (s/n)");
                        command2 = sc.nextLine();
                        if(command2.equals("s"))
                        {
                            alunos.remove(i);
                        }
                        System.out.print("\nUsuário removido.\n");
                    }
                }
                
                if(found_user == false)
                {
                    System.out.print("\n-----------------------\n");
                    System.out.print("Usuário não encontrado.");
                    System.out.print("\n-----------------------\n");
                }
            }
        }
    }
}
