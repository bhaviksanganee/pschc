
var redraw, g, renderer;

/* only do all this when document has finished loading (needed for RaphaelJS) */
window.onload = function() {
	var host = $('#host').val();
    if (host == "wdc-esxcpd-dhcp-24251"){
	var input = [{"site_cn":"Site1", "pscServers":"null", "pscReplicationServers":{"wdc-esxcpd-dhcp-24246.eng.vmware.com": [],"wdc-esxcpd-dhcp-24255.eng.vmware.com": ["wdc-esxcpd-dhcp-24251.eng.vmware.com","wdc-esxcpd-dhcp-24246.eng.vmware.com","wdc-esxcpd-dhcp-24245.eng.vmware.com"],"wdc-esxcpd-dhcp-24251.eng.vmware.com": ["wdc-esxcpd-dhcp-24255.eng.vmware.com"]}, "services":"[cs.componentmanager, sca, applmgmt, cs.syslog, cs.componentmanager, sca, applmgmt, cs.syslog, cs.componentmanager, sca, applmgmt, cs.syslog, cs.license, cs.license, cs.license, cs.identity, cs.identity, cs.identity]", "lbPSC":{"wdc-esxcpd-dhcp-24247.eng.vmware.com": ["wdc-esxcpd-dhcp-24255.eng.vmware.com","wdc-esxcpd-dhcp-24246.eng.vmware.com","wdc-esxcpd-dhcp-24251.eng.vmware.com"]}},{"site_cn":"Site2", "pscServers":"null", "pscReplicationServers":{"wdc-esxcpd-dhcp-24245.eng.vmware.com": ["wdc-esxcpd-dhcp-24255.eng.vmware.com"]}, "services":"[cs.identity, cs.componentmanager, cs.license, sca, applmgmt, cs.syslog]", 
	"lbPSC":{"wdc-esxcpd-dhcp-24247.eng.vmware.com": ["wdc-esxcpd-dhcp-24255.eng.vmware.com","wdc-esxcpd-dhcp-24246.eng.vmware.com","wdc-esxcpd-dhcp-24251.eng.vmware.com"]}}];
    }else{ 
		var input = JSON.parse( $('#siteNodeData').val());
	}
	
 var render = function(r, n) {
            /* the Raphael set is obligatory, containing all you want to display */
            var set = r.set().push(
                /* custom objects go here */
                r.rect(n.point[0]-30, n.point[1]-13, 250, 100).attr({"fill": "#feb", r : "12px", "stroke-width" : n.distance == 0 ? "3px" : "1px" })).push(
                r.text(n.point[0] + 80, n.point[1] +20 , (n.label || n.id) )/*.attr({"font-size":"15px"})*/);
            return set;
        };
		
 var renderHa = function(r, n) {
            /* the Raphael set is obligatory, containing all you want to display */
            var set = r.set().push(
                /* custom objects go here */
                r.rect(n.point[0]-30, n.point[1]-13, 250, 100).attr({"fill": "#99ccff", r : "12px", "stroke-width" : n.distance == 0 ? "3px" : "1px" })).push(
                r.text(n.point[0] + 80, n.point[1] +20 , (n.label || n.id) )/*.attr({"font-size":"15px"})*/);
            return set;
        };
 
 function getGraphForSites(inp){
	 Graph.Renderer.defaultRenderFunc = render;
	 g = new Graph();
	 g.edgeFactory.template.style.directed = true;
	 
	var nodes = []; 
	var edges = []
	inp.forEach(function(site){
		
		var siteName = site["site_cn"];
		var  pscReplicationServers = site["pscReplicationServers"];
		
		var lbPsc = site["lbPSC"];
		
		var servers = Object.keys(pscReplicationServers);
		
		servers.forEach(function(server){
			
			
		});
		
		var lbServers = Object.keys(lbPsc);
		lbServers.forEach(function(server){
			nodes.push({id:server,arg:{label: server+"\n Site name : "+siteName+"\n Type : HA ***",render:renderHa}});
			var reps = lbPsc[server];
			reps.forEach(function(rep){				
				edges.push({s:server,r:rep,arg:{label:"load balancer", stroke : "#bfa" , fill : "#56f"}});				
			});
		});
		servers.forEach(function(server){
			nodes.push({id:server,arg:{label: server+"\n Site name : "+siteName,render:render}});
			var reps = pscReplicationServers[server];
			reps.forEach(function(rep){				
				edges.push({s:server,r:rep,arg:{label:"replication"}});				
			});
			
		});
		
	});
	
	nodes.forEach(function(node){
		g.addNode(node['id'],node['arg']);
	});
	
	edges.forEach(function(edge){
		g.addEdge(edge['s'],edge['r'],edge['arg']);
	});
	
	return g;
	 
 }

	
    var width = $(document).width() -200;
    var height = $(document).height() -100;
    
    g = getGraphForSites(input);

    /* layout the graph using the Spring layout implementation */
    var layouter = new Graph.Layout.Spring(g);
    
    /* draw the graph using the RaphaelJS draw implementation */
    renderer = new Graph.Renderer.Raphael('canvas', g, width, height);
    
    redraw = function() {
        layouter.layout();
        renderer.draw();
    };
    hide = function(id) {
        g.nodes[id].hide();
    };
    show = function(id) {
        g.nodes[id].show();
    };
    //    console.log(g.nodes["kiwi"]);
};

