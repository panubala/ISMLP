let $root := doc('inproceedings.xml')/root
return <root>{
  
  for $i in $root//*
  return
    if (some $el in $i//* satisfies (contains($el, '1987')))
    then $i
    else ()
    
}</root>