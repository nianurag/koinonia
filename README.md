# koinonia

## Setup Database

```
CREATE DATABASE IF NOT EXISTS 'chatdb' ;
```

```
  CREATE TABLE IF NOT EXISTS `User` (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `nick` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `sessionId` varchar(200) NOT NULL,
  `lastLogin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `accountType` varchar(255) NOT NULL DEFAULT 'basic',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nick` (`nick`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
```

## Running

To start a web server for the application, run:

    lein run

To start the angular client, run:

    grunt serve

## License

Copyright © 2015 FIXME
