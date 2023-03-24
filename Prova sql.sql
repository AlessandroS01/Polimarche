create table gps(
    position point,
    speed float,
    setup_code int,
    primary key (position, speed, setup_code)
);

select * from member;

insert into member values('19012323', 'ciao', 'l', 'as', '2001-10-10', 'aja@', '333723723', 'Magazzino');

alter table member drop constraint member_ibfk_1;

alter table member add
CONSTRAINT `member_ibfk_1` FOREIGN KEY (`workshop_area`) REFERENCES `workshop` (`area_name`) ON DELETE RESTRICT ON UPDATE CASCADE;