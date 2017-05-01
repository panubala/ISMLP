let $publications := doc('publications.xml')/root//*
return <root>{
  for $p in $publications
  return
    if (contains($p/id, "conf"))
    then $p
    else ()
}</root>