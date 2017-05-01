let $root := doc('../database.xml')/root
return <root>{

for $proceedings in $root//proceedings
return <proceedings>{
  <id>{data($proceedings/@key)}</id>,
  <title>{$proceedings/title/text()}</title>,
  <cid>{$proceedings/booktitle/text()}</cid>,
  <ceid>{$proceedings/year/text()}</ceid>,
  <sid>{$proceedings/series/text()}</sid>,
  <publisher>{$proceedings/publisher/text()}</publisher>,
  <isbn>{$proceedings/isbn/text()}</isbn>,
  for $editor in $proceedings//editor
  return <editor>{$editor/text()}</editor>
}</proceedings>

}</root>