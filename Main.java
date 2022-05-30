import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main
{   
    public Main()
    {
        Scanner sc=new Scanner(System.in);
        
        String BirthDateHolder;
            
        String Name; 
        String TelephoneNumber = ""; 
        float FinalGrade;
            
        // Variables for dates
        int Day;
        int Month;
        int Year;
        String BirthDate = "";
        String LastEdit = "";
        
        // variable for ExceptionA handling
        String MessageA;
        boolean ExceptionA = true;
        
        // variable for looking up userNames
        String UserNameLookup;
        boolean FoundUser = false;
        List<Pessoa> Pessoas = new ArrayList<Pessoa>();
        List<Aluno> Alunos = new ArrayList<Aluno>();
        
        
        // people for testing
        Pessoa Person1 = new Pessoa("John doe", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", -1);
        Pessoa Person2 = new Pessoa("Admin", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", -1);
        Pessoas.add(Person1);
        Pessoas.add(Person2);
        
        // students for testing
        Aluno Student1 = new Aluno("John doe (student)", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", 45);
        Aluno Student2 = new Aluno("Admin (student)", "+456432456", "23/5432/45654", "14/01/2022", "14/01/2022", 90);
        Alunos.add(Student1);
        Alunos.add(Student2);
        
        // secondary Commands
        String Command2 = "";
        
        while(true)
        {
            LocalDateTime ldt = LocalDateTime.now();
            System.out.print("\n============================================================\n");
            System.out.print("Escreva 'criar' para criar uma Pessoa ou Aluno\n");
            System.out.print("Escreva 'ver' para mostrar Alunos e Pessoas\n");
            System.out.print("Escreva 'editar' para mudar os dados de uma Pessoa ou Aluno\n");
            System.out.print("Escreva 'deletar' para deletar uma Pessoa ou Aluno\n");
            System.out.print("Escreva '0' para sair\n\n");
            System.out.print("Comando: ");
            String Command = sc.nextLine();
            
            if(Command.equals("0"))
            {
                break;
            }
            
            if(Command.equals("criar")) 
            {
                System.out.flush();
                System.out.print("Informe o nome da Pessoa: ");
                Name = sc.nextLine();
                
                MessageA = "Informe o número de telefone da Pessoa: ";
                while(DataValidator.ValidatePhoneNumber(TelephoneNumber) == false)
                {
                    System.out.flush();
                    System.out.print(MessageA);
                    TelephoneNumber = sc.nextLine();
                    MessageA = "Informe um número de telefone válido: ";
                }

                MessageA = "Informe a data de nascimento da Pessoa (no formato dia/mes/ano): ";
                while(ExceptionA == true)
                {
                    try
                    {
                        System.out.flush();
                        System.out.print(MessageA);
                        BirthDateHolder = sc.nextLine();
                        String[] birth_buffer = BirthDateHolder.split("/");
                        Day = Integer.parseInt(birth_buffer[0]);
                        Month = Integer.parseInt(birth_buffer[1]);
                        Year = Integer.parseInt(birth_buffer[2]);
                        BirthDate = BirthDateHolder;
                        ExceptionA = false;
                    }
                    catch(Exception e)
                    {
                        ExceptionA = true;
                        MessageA = "Informe uma data de nascimento válida (no formato dia/mes/ano): ";
                    }
                }
                
                // Defining the register date, which is the same as the last edit, since registering is considered editing.
                String LastEdit_str = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ldt);
                LastEdit = LastEdit_str;
                
                System.out.print("\nDeseja informar a nota final do curso? (s/n): ");
                String resposta = sc.nextLine();
                if(resposta.equals("s")) // Creates a student if they tell the grade
                {
                    MessageA = "\nInforme a nota final do Aluno: \n";
                    ExceptionA = true;
                    
                    while(ExceptionA == true)
                    {
                        try 
                        {
                            System.out.flush();
                            System.out.print(MessageA);
                            FinalGrade = Float.parseFloat(sc.nextLine());
                            Aluno student = new Aluno(Name, TelephoneNumber, BirthDate, LastEdit, LastEdit, FinalGrade);
                            Alunos.add(student);
                            ExceptionA = false;
                        }
                        catch(Exception e)
                        {
                            ExceptionA = true;
                            MessageA = "\nInforme uma nota final válida: \n";
                        }
                    }
                }
                else // Creates a person if they don't want to tell the grade, and defines it as -1.
                {
                    Pessoa person = new Pessoa(Name, TelephoneNumber, BirthDate, LastEdit, LastEdit, -1);
                    Pessoas.add(person);
                }
            }
            
            if(Command.equals("ver")) 
            {
                System.out.flush();
                System.out.print("Pessoas:\n\n");
                
                for(int i = 0; i < Pessoas.size(); i++) // Iterate through people
                {
                    System.out.print("Nome: " + Pessoas.get(i).Name + "\n");
                    System.out.print("Telefone: " + Pessoas.get(i).TelephoneNumber + "\n");
                    System.out.print("Data de nascimento: " + Pessoas.get(i).BirthDate + "\n");
                    System.out.print("Data da última edição: " + Pessoas.get(i).register_date + "\n\n");
                }
                
                System.out.print("Alunos:\n\n");
                
                for(int i = 0; i < Alunos.size(); i++) // Iterate through students
                {
                    System.out.print("Nome: " + Alunos.get(i).Name + "\n");
                    System.out.print("Telefone: " + Alunos.get(i).TelephoneNumber + "\n");
                    System.out.print("Nota final: " + String.valueOf(Alunos.get(i).FinalGrade)  + "\n");
                    System.out.print("Data de nascimento: " + Alunos.get(i).BirthDate + "\n");
                    System.out.print("Data da última edição: " + Alunos.get(i).register_date + "\n\n");
                }
            }
            
            if(Command.equals("editar")) // Edit student and people's data.
            {
                FoundUser = false;
                System.out.print("Escreva o nome do usuário que você deseja editar: ");
                UserNameLookup = sc.nextLine();
                
                for(int i = 0; i < Pessoas.size(); i++) // Look for people with that Name
                {
                    if(Pessoas.get(i).Name.equals(UserNameLookup)) 
                    {
                        FoundUser = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está editando o seguinte usuário (uma Pessoa):\n");
                        System.out.print("Nome: " + Pessoas.get(i).Name + "\n");
                        System.out.print("Telefone: " + Pessoas.get(i).TelephoneNumber + "\n");
                        System.out.print("Data de nascimento: " + Pessoas.get(i).BirthDate + "\n");
                        System.out.print("Data da última edição: " + Pessoas.get(i).register_date + "\n\n");
                        System.out.print("Para editar o nome, digite nome\n");
                        System.out.print("Para editar o telefone, digite telefone\n");
                        System.out.print("Para editar a data de nascimento, digite nascimento\n");
                        System.out.print("Para cancelar, digite 0\n");
                        Command2 = sc.nextLine();
                        
                        if(Command2.equals("0")) // In case that's not the person they wanna edit.
                        {
                            break;
                        }
                        
                        if(Command2.equals("nome"))
                        {
                            /*
                            In this if statement I'm gonna use a lot of nested else statements
                            this is because if you wanted to change a user's Name to "telephone"
                            then without the else statements, it would end up making you edit their phone number
                               */
                            System.out.print("\n\nEscreva o novo nome deste usuário: \n");
                            Command2 = sc.nextLine();
                            Pessoas.get(i).SetName(Command2);
                        }
                        else
                        {
                            if(Command2.equals("telefone"))
                            {
                                System.out.print("\n\nEscreva o novo telefone deste usuário: \n");
                                Command2 = sc.nextLine();
                                Pessoas.get(i).SetPhone(Command2);
                            }
                            else
                            {
                                if(Command2.equals("nascimento"))
                                {
                                    System.out.print("\n\nEscreva a nova data de nascimento deste usuário: \n");
                                    Command2 = sc.nextLine();
                                    Pessoas.get(i).SetBirthDate(Command2);
                                }
                                else
                                {
                                    System.out.print("\n\nComando inválido, voltando ao início.\n");
                                }
                            }
                        }
                    }
                }
                
                for(int i = 0; i < Alunos.size(); i++) // Look for STUDENTS with that Name
                {
                    if(Alunos.get(i).Name.equals(UserNameLookup)) 
                    {
                        FoundUser = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está editando o seguinte usuário (um Aluno):\n");
                        System.out.print("Nome: " + Alunos.get(i).Name + "\n");
                        System.out.print("Telefone: " + Alunos.get(i).TelephoneNumber + "\n");
                        System.out.print("Data de nascimento: " + Alunos.get(i).BirthDate + "\n");
                        System.out.print("Data da última edição: " + Alunos.get(i).register_date + "\n\n");
                        System.out.print("Para editar o nome, digite nome\n");
                        System.out.print("Para editar o telefone, digite telefone\n");
                        System.out.print("Para editar a data de nascimento, digite nascimento\n");
                        System.out.print("Para editar a nota final, digite nota\n");
                        System.out.print("Para cancelar, digite 0\n");
                        Command2 = sc.nextLine();
                        
                        if(Command2.equals("0")) // In case that's not the person they wanna edit.
                        {
                            break;
                        }
                        
                        if(Command2.equals("nome"))
                        {
                            /*
                            In this if statement I'm gonna use a lot of nested else statements
                            this is because if you wanted to change a user's Name to "telephone"
                            then without the else statements, it would end up making you edit their phone number
                               */
                            System.out.print("\n\nEscreva o novo nome deste usuário: \n");
                            Command2 = sc.nextLine();
                            Alunos.get(i).SetName(Command2);
                        }
                        else
                        {
                            if(Command2.equals("telefone"))
                            {
                                System.out.print("\n\nEscreva o novo telefone deste usuário: \n");
                                Command2 = sc.nextLine();
                                Alunos.get(i).SetPhone(Command2);
                            }
                            else
                            {
                                if(Command2.equals("nascimento"))
                                {
                                    System.out.print("\n\nEscreva a nova data de nascimento deste usuário: \n");
                                    Command2 = sc.nextLine();
                                    Alunos.get(i).SetBirthDate(Command2);
                                }
                                else
                                {
                                    if(Command2.equals("nota"))
                                    {
                                        System.out.print("\n\nEscreva a nova nota final deste Aluno: \n");
                                        Command2 = sc.nextLine();
                                        Alunos.get(i).SetGrade(Command2);
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
                
                if(FoundUser == false)
                {
                    System.out.print("\n-----------------------\n");
                    System.out.print("Usuário não encontrado.");
                    System.out.print("\n-----------------------\n");
                }
            }
            
            if(Command.equals("deletar")) // Edit student and people's data.
            {
                FoundUser = false;
                System.out.print("Escreva o nome do usuário que você deseja deletar: ");
                UserNameLookup = sc.nextLine();
                
                for(int i = 0; i < Pessoas.size(); i++) // Look for people with that Name
                {
                    if(Pessoas.get(i).Name.equals(UserNameLookup)) 
                    {
                        FoundUser = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está visualizando o seguinte usuário (uma Pessoa):\n");
                        System.out.print("Nome: " + Pessoas.get(i).Name + "\n");
                        System.out.print("Telefone: " + Pessoas.get(i).TelephoneNumber + "\n");
                        System.out.print("Data de nascimento: " + Pessoas.get(i).BirthDate + "\n");
                        System.out.print("Data da última edição: " + Pessoas.get(i).register_date + "\n\n");
                        System.out.print("\n\nVocê deseja deletar este usuário? (s/n)");
                        Command2 = sc.nextLine();
                        if(Command2.equals("s"))
                        {
                            Pessoas.remove(i);
                        }
                        System.out.print("\nUsuário removido.\n");
                    }
                }
                
                for(int i = 0; i < Alunos.size(); i++) // Look for STUDENTS with that Name
                {
                    if(Alunos.get(i).Name.equals(UserNameLookup)) 
                    {
                        FoundUser = true;
                        System.out.print("============================================================\n");
                        System.out.print("Você está visualizando o seguinte usuário (um Aluno):\n");
                        System.out.print("Nome: " + Alunos.get(i).Name + "\n");
                        System.out.print("Telefone: " + Alunos.get(i).TelephoneNumber + "\n");
                        System.out.print("Data de nascimento: " + Alunos.get(i).BirthDate + "\n");
                        System.out.print("Data da última edição: " + Alunos.get(i).register_date + "\n\n");
                        System.out.print("\n\nVocê deseja deletar este usuário? (s/n)");
                        Command2 = sc.nextLine();
                        if(Command2.equals("s"))
                        {
                            Alunos.remove(i);
                        }
                        System.out.print("\nUsuário removido.\n");
                    }
                }
                
                if(FoundUser == false)
                {
                    System.out.print("\n-----------------------\n");
                    System.out.print("Usuário não encontrado.");
                    System.out.print("\n-----------------------\n");
                }
            }
        }
    }
}
