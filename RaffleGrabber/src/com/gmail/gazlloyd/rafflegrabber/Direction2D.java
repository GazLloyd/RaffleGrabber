package com.gmail.gazlloyd.rafflegrabber;

import java.awt.Point;

/*
 * Adapted from Direction2D in the BankStitcher program by a_proofreader
 * http://rs.wikia.com/User:a_proofreader
 */

public enum Direction2D {
    NORTH {
        @Override
        public Point move(Point paramPoint) {
            return new Point(paramPoint.x, paramPoint.y+1);
        }
    },

    EAST {
        @Override
        public Point move(Point paramPoint) {
            return new Point(paramPoint.x+1, paramPoint.y);
        }
    },

    SOUTH {
        @Override
        public Point move(Point paramPoint) {
            return new Point(paramPoint.x, paramPoint.y-1);
        }
    },

    WEST {
        @Override
        public Point move(Point paramPoint) {
            return new Point(paramPoint.x-1, paramPoint.y);
        }
    };

    public abstract Point move(Point paramPoint);
}