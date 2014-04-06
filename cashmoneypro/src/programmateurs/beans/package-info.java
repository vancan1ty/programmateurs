/**
 * This package contains "beans", which for the purposes of our app are simple
 * data objects designed to shuffle data between our app and the database.
 * 
 * The design of the objects is generally immutable.  Changes to state can occur
 * by copying the objects through their constructors and changing desired
 * fields.  In general we seek to be very careful to make sure that changes
 * to state are always reflected in the database.  DATA IS KING.  Keep it safe.
 *  
 * @author vancan1ty
 *
 */
package programmateurs.beans;