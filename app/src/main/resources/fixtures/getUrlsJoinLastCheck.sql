SELECT urls.id, urls.name, url_check_last.status_code, url_check_last.created_at
FROM urls
	LEFT JOIN
		(SELECT u_ch.* FROM url_checks u_ch
			INNER JOIN
				(SELECT url_id, MAX(created_at) as last_check_dttm
				FROM url_checks GROUP BY url_id) u_ch_last
			ON u_ch.url_id = u_ch_last.url_id AND u_ch.created_at = u_ch_last.last_check_dttm
		) url_check_last
	ON urls.id = url_check_last.url_id
ORDER BY id;
