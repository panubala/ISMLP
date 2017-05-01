let $author := doc('persons.xml')/root//*[id = 'Graham Greenleaf']
return <root>{
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