let $inproceedings :=doc('inproceedings.xml')/root/*
return <root>{
  for $ip in $inproceedings
  where some $author in $ip/author[last()] satisfies $author/text() = 'Charles S. Saxon'
  return $ip
}</root>