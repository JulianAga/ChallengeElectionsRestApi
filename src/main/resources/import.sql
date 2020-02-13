insert into candidates(first_name, last_name) values ("Mauricio","Macri");
insert into candidates(first_name, last_name) values ("Alberto","Fernandez");
insert into elections(id,start_date, end_date) values (1,"2020-02-21 08:00:00", "2020-02-21 08:30:00");
insert into candidates_by_elections(id,candidate_id, election_id) values (1,1,1),(2,2,1);
