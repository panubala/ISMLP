let $authors := doc('persons.xml')/root//*

return <root>{
  for $author in $authors
  return
    if (empty($author/id))
    then()
    else
      <author>{
        $author/id,
        for $i in doc('inproceedings.xml')/root//*[id = $author//iid/text()]
        return
            for $coAuthor in $i//author
            return
              if ($coAuthor/text() = $author/id/text())
              then ()
              else <coAuthor>{$coAuthor/text()}</coAuthor>
      }</author>
}</root>