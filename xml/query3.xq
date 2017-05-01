let $publications := doc('publications.xml')/root//*[contains(id, 'concur')]
return <root>{
  let $sorted := for $publication in $publications
     order by $publication/id/text()
     return $publication
  return subsequence($sorted, 10, 10)
}</root>