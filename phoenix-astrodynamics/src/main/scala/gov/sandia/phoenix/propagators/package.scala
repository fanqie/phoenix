package gov.sandia.phoenix

/**
 * A propagator is a function that optionally returns state (i.e. ECI Position and Velocity) as a function of time (JD).
 *
 * A propagator is an object that is used to compute the location/state of a
 * satellite.
 *
 * Some typical propagators are:
 *
 * Kepler Elements: Idealized two-body elements using Kepler's Laws. Note that
 * without an epoch to reference the elements in time, you cannot propagate keps.
 *
 * ECI State Vector: Position and Velocity representation of a state. Can be
 * exchanged with Kepler elements for same solution two body propagation. Can
 * also be propagated using a force model for high accuracy propagation. Also
 * requires an epoch.
 *
 * TLE: Two Line Elements - A string based format that has some extra terms for
 * things like drag and better than two-body geometry. Sometimes has a line 0
 * for the name of the satellite, but that is optional. Epoch is included.
 *
 * Almanac/Ephemeris: GPS specific propagators that are very precise. Almanac is
 * pretty good and is valid for months. Ephemeris is very precise and is only
 * good for a few hours.
 *
 * SP3: Historical GPS observations that can be interpolated using Lagrange
 * polynomials for extremely precise state calculations. SP3s are published days
 * or weeks after the actual orbit, so are historical in nature.
 */
package object propagators {}
