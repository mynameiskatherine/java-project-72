select distinct on (url_id) * from url_checks
		order by url_id desc, id desc;
