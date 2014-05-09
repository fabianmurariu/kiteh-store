'use strict';

var mock = {
    posts : [
        {"id": "_7nsC7GeXlyS5vZJ9A3AABb", "title": "ultricies lorem duis pellentesque ultricies", "description": "vehicula morbi odio praesent montes sollicitudin posuere facilisis aptent tempus turpis ullamcorper nisi auctor vestibulum", "currency": "RON", "price": 51044, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.202Z", "updated": "2014-04-28T11:58:18.224Z", "tags": ["vestibulum", "nullam", "tempor", "diam"], "images": [
            {"id": "_7nsC7GeXlyS5vZJ9A3AABb18", "caption": "vehicula tempus laoreet vivamus", "sizes": [
                {"width": 500, "height": 900, "url": "http://placekitten.com/500/900"}
            ]},
            {"id": "_7nsC7GeXlyS5vZJ9A3AABb19", "caption": "nostra auctor tempus fames", "sizes": [
                {"width": 700, "height": 500, "url": "http://placekitten.com/700/500"}
            ]}
        ]},
        {"id": "_65lPsTuoWFx_5Be6Hn3ObIy", "title": "enim molestie feugiat integer euismod", "description": "molestie ut commodo ligula maecenas leo euismod mi metus hac cum aliquam ipsum velit lacinia", "currency": "RON", "price": 64665, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.225Z", "updated": "2014-04-28T11:58:18.225Z", "tags": ["ad", "congue", "vitae", "tortor"], "images": [
            {"id": "_65lPsTuoWFx_5Be6Hn3ObIy7", "caption": "sit scelerisque aenean accumsan", "sizes": [
                {"width": 900, "height": 900, "url": "http://placekitten.com/900/900"}
            ]},
            {"id": "_65lPsTuoWFx_5Be6Hn3ObIy8", "caption": "nisl neque auctor aenean", "sizes": [
                {"width": 900, "height": 900, "url": "http://placekitten.com/900/900"}
            ]}
        ]},
        {"id": "_47NxRndgt3x_6=S|VYelXwg", "title": "praesent arcu placerat dapibus ligula", "description": "nisi consequat aptent facilisis non lacus lacus conubia ipsum sem ligula scelerisque arcu lectus ac", "currency": "RON", "price": 8443, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.226Z", "updated": "2014-04-28T11:58:18.226Z", "tags": ["nibh", "sit", "vitae", "diam"], "images": [
            {"id": "_47NxRndgt3x_6=S|VYelXwg8", "caption": "leo lacus imperdiet ornare", "sizes": [
                {"width": 500, "height": 700, "url": "http://placekitten.com/500/700"}
            ]},
            {"id": "_47NxRndgt3x_6=S|VYelXwg9", "caption": "tempor iaculis vel eros", "sizes": [
                {"width": 500, "height": 500, "url": "http://placekitten.com/500/500"}
            ]}
        ]},
        {"id": "_49oOfGD9REh1ZMeLtrGACY", "title": "duis ornare arcu sodales ipsum", "description": "quam cum hendrerit iaculis etiam etiam curabitur hac at vitae auctor arcu consequat nisi sodales", "currency": "RON", "price": 18536, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.227Z", "updated": "2014-04-28T11:58:18.227Z", "tags": ["arcu", "justo", "neque", "nec"], "images": [
            {"id": "_49oOfGD9REh1ZMeLtrGACY30", "caption": "mattis at bibendum porttitor", "sizes": [
                {"width": 500, "height": 900, "url": "http://placekitten.com/500/900"}
            ]},
            {"id": "_49oOfGD9REh1ZMeLtrGACY31", "caption": "amet pellentesque at faucibus", "sizes": [
                {"width": 900, "height": 600, "url": "http://placekitten.com/900/600"}
            ]}
        ]},
        {"id": "_5qYJt2S8ATW_6rPI6158HS5", "title": "nostra posuere cras mauris fermentum", "description": "porta elementum montes potenti ad porta varius venenatis semper ridiculus ipsum lacinia sollicitudin libero nibh", "currency": "RON", "price": 103578, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.228Z", "updated": "2014-04-28T11:58:18.228Z", "tags": ["ac", "torquent", "maecenas", "torquent"], "images": [
            {"id": "_5qYJt2S8ATW_6rPI6158HS519", "caption": "placerat id justo tortor", "sizes": [
                {"width": 800, "height": 500, "url": "http://placekitten.com/800/500"}
            ]},
            {"id": "_5qYJt2S8ATW_6rPI6158HS520", "caption": "euismod neque ante diam", "sizes": [
                {"width": 700, "height": 700, "url": "http://placekitten.com/700/700"}
            ]}
        ]},
        {"id": "_7AT479XSsBq1FcMUvP4QZT", "title": "vehicula dictumst pellentesque volutpat penatibus", "description": "metus suscipit metus dis conubia litora viverra himenaeos aptent rhoncus iaculis habitant duis dui mauris", "currency": "RON", "price": 56768, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.229Z", "updated": "2014-04-28T11:58:18.229Z", "tags": ["nullam", "scelerisque", "sem", "cras"], "images": [
            {"id": "_7AT479XSsBq1FcMUvP4QZT14", "caption": "a hendrerit cras lobortis", "sizes": [
                {"width": 600, "height": 900, "url": "http://placekitten.com/600/900"}
            ]},
            {"id": "_7AT479XSsBq1FcMUvP4QZT15", "caption": "natoque praesent tempus consectetur", "sizes": [
                {"width": 900, "height": 500, "url": "http://placekitten.com/900/500"}
            ]}
        ]},
        {"id": "_6zyYXjEzjCE3CnYlcydkIh", "title": "ligula laoreet sociis pellentesque cubilia", "description": "quis ridiculus arcu dui tristique ante ridiculus cursus lorem nascetur fames ante himenaeos fringilla nisl", "currency": "RON", "price": 110276, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.230Z", "updated": "2014-04-28T11:58:18.230Z", "tags": ["lacus", "rhoncus", "luctus", "pulvinar"], "images": [
            {"id": "_6zyYXjEzjCE3CnYlcydkIh22", "caption": "magna mollis sapien feugiat", "sizes": [
                {"width": 500, "height": 600, "url": "http://placekitten.com/500/600"}
            ]},
            {"id": "_6zyYXjEzjCE3CnYlcydkIh23", "caption": "nam faucibus curabitur est", "sizes": [
                {"width": 600, "height": 500, "url": "http://placekitten.com/600/500"}
            ]}
        ]},
        {"id": "_6l4wimnjCVX_6n6qR3aab9u", "title": "cras class litora justo varius", "description": "in per scelerisque natoque non lacus dui netus imperdiet ultricies magna platea mauris himenaeos malesuada", "currency": "RON", "price": 4703, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.231Z", "updated": "2014-04-28T11:58:18.231Z", "tags": ["consequat", "mollis", "commodo", "sapien"], "images": [
            {"id": "_6l4wimnjCVX_6n6qR3aab9u14", "caption": "quam sodales dignissim et", "sizes": [
                {"width": 700, "height": 900, "url": "http://placekitten.com/700/900"}
            ]},
            {"id": "_6l4wimnjCVX_6n6qR3aab9u15", "caption": "dictumst ante vivamus rhoncus", "sizes": [
                {"width": 800, "height": 600, "url": "http://placekitten.com/800/600"}
            ]}
        ]},
        {"id": "_4uBVfJihdz23lMY1TBxQJO", "title": "porta id fermentum maecenas quam", "description": "metus potenti egestas pretium sed tempor maecenas est eu arcu convallis gravida suspendisse fringilla sodales", "currency": "RON", "price": 57528, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.231Z", "updated": "2014-04-28T11:58:18.231Z", "tags": ["laoreet", "laoreet", "diam", "pretium"], "images": [
            {"id": "_4uBVfJihdz23lMY1TBxQJO19", "caption": "magnis luctus condimentum aptent", "sizes": [
                {"width": 500, "height": 900, "url": "http://placekitten.com/500/900"}
            ]},
            {"id": "_4uBVfJihdz23lMY1TBxQJO20", "caption": "vitae parturient inceptos est", "sizes": [
                {"width": 800, "height": 900, "url": "http://placekitten.com/800/900"}
            ]}
        ]},
        {"id": "_5xNh|EzV7yL4jarqrzMQ|9", "title": "torquent etiam nisl euismod natoque", "description": "fusce eget fringilla feugiat aliquam class gravida sodales purus urna porta cubilia dictum donec tellus", "currency": "RON", "price": 33380, "quantity": 1, "ttl": 7, "created": "2014-04-28T11:58:18.232Z", "updated": "2014-04-28T11:58:18.232Z", "tags": ["vitae", "rhoncus", "quam", "est"], "images": [
            {"id": "_5xNh|EzV7yL4jarqrzMQ|926", "caption": "scelerisque dolor aptent senectus", "sizes": [
                {"width": 900, "height": 800, "url": "http://placekitten.com/900/800"}
            ]},
            {"id": "_5xNh|EzV7yL4jarqrzMQ|927", "caption": "lobortis diam vivamus cursus", "sizes": [
                {"width": 800, "height": 800, "url": "http://placekitten.com/800/800"}
            ]}
        ]}
    ]
};
