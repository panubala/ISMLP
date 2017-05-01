let $authors := doc('persons.xml')/root//*[id/text() = "R. D. Purdy"]

return $authors