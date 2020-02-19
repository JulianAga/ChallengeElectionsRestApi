insert into candidates(first_name, last_name) values ("Mauricio","Macri");
insert into candidates(first_name, last_name) values ("Alberto","Fernandez");
insert into elections(id,start_date, end_date) values (1,"2020-02-21 08:00:00", "2020-02-21 08:30:00");
insert into candidates_by_elections(id,candidate_id, election_id) values (1,1,1),(2,2,1);
insert into users(id,age,day_of_birth,email,first_name,last_name) values ("3df8347c-9f94-47f1-b604-613fb4e835fe",20,"1999-06-30","julianulisesaga@gmail.com","Julian","Aga");