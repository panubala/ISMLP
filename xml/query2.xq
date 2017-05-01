let $publications := doc('publications.xml')/root//*[contains(id, 'concur')]
return <root>{
  subsequence($publications, 10, 10)
}</root>