package gov.sandia.phoenix.geometry

import org.scalatest.FunSuite

class ArcTest extends FunSuite {
  test("contains") {
    assert(Arc(X_AXIS, Y_AXIS).contains(Vector3(1, 1, 0)))
    assert(Arc(Y_AXIS, X_AXIS).contains(Vector3(1, 1, 0)))
    assert(!Arc(X_AXIS, Y_AXIS).contains(Vector3(1, 1, 1)))
    assert(!Arc(Y_AXIS, X_AXIS).contains(Vector3(1, 1, 1)))
    assert(!Arc(X_AXIS, Y_AXIS).contains(Vector3(-1, 0, 0)))
    assert(!Arc(Y_AXIS, X_AXIS).contains(Vector3(-1, 0, 0)))
    assert(!Arc(X_AXIS, Y_AXIS).contains(Vector3(0, -1, 0)))
    assert(!Arc(Y_AXIS, X_AXIS).contains(Vector3(0, -1, 0)))
    assert(!Arc(X_AXIS, Y_AXIS).contains(Vector3(0, 0, 0)))
    assert(!Arc(Y_AXIS, X_AXIS).contains(Vector3(0, 0, 0)))
    assert(Arc(X_AXIS, Y_AXIS).contains(X_AXIS))
  }

  test("intersection"){
    val midlop = (X_AXIS * 2 + Y_AXIS + Z_AXIS).normalized
    val midlon = (X_AXIS * 2 + Y_AXIS - Z_AXIS).normalized
    assert(!Arc(-X_AXIS, (X_AXIS + Y_AXIS).normalized).intersects(Arc(midlop, midlon)))
    assert(Arc(X_AXIS, (-X_AXIS + Y_AXIS).normalized).intersects(Arc(midlop, midlon)))
    assert(Arc(X_AXIS, (X_AXIS + Y_AXIS).normalized).intersects(Arc(midlop, midlon)))
    assert(!Arc(-X_AXIS, (-X_AXIS + Y_AXIS).normalized).intersects(Arc(midlop, midlon)))
    assert(
      Arc((X_AXIS + Y_AXIS).normalized, (-X_AXIS + Y_AXIS).normalized) intersects
      Arc((Y_AXIS + Z_AXIS).normalized, (Y_AXIS - Z_AXIS).normalized))
  }

  test("self-intersection"){
    assert(
      Arc((X_AXIS + Y_AXIS).normalized, (-X_AXIS + Y_AXIS).normalized) intersects
        Arc((X_AXIS + Y_AXIS).normalized, (-X_AXIS + Y_AXIS).normalized))

    val a = Arc(X_AXIS, Y_AXIS)
    assert(a.intersects(a))
  }

  test("end intersection"){
    val arc = Arc(X_AXIS, Y_AXIS)
    assert(arc.contains(X_AXIS))
    //assert(arc.contains(Y_AXIS))
  }

  test("two intersections ++0"){
    val plane = Plane(0.95, Y_AXIS + X_AXIS)
    val arc = Arc(X_AXIS, Y_AXIS)
    val ans = arc.generalIntersection(plane)
    assert(ans.length == 2)
    ans foreach { a => assert(plane.dist(a) <= 1.0E-10) }
  }

  test("two intersections +-0"){
    val plane = Plane(0.95, Y_AXIS - X_AXIS)
    val arc = Arc(Y_AXIS, -X_AXIS)
    val ans = arc.generalIntersection(plane)
    assert(ans.length == 2)
    ans foreach { a => assert(plane.dist(a) <= 1.0E-10) }
  }

  test("two intersections --0"){
    val plane = Plane(0.95, - Y_AXIS - X_AXIS)
    val arc = Arc(-Y_AXIS, -X_AXIS)
    val ans = arc.generalIntersection(plane)
    assert(ans.length == 2)
    ans foreach { a => assert(plane.dist(a) <= 1.0E-10) }
  }
}
