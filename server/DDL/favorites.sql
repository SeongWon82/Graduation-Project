create table favorites(
	id varchar(20),
	route_id varchar(30),
	trans_type int,
	FOREIGN KEY (id) REFERENCES user(id)
)
