package io.kindedj;


/**
 * Methods to normalize HKT encodings that extends KindedJ encoding with subtyping aliases, using the fact that
 * {@link Hk} is covariant in its first type variable argument (witness type of type constructor)
 * because of the Liskov substitution principle.
 */
public final class HCov {
  private HCov(){}

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B> Hk<Hk<f, A>, B> covary2(Hk<? extends Hk<f, A>, B> hk) {
    return (Hk<Hk<f, A>, B>) hk;
  }

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B, C> Hk<Hk<Hk<f, A>, B>, C> covary3(Hk<? extends Hk<? extends Hk<f, A>, B>, C> hk) {
    return (Hk<Hk<Hk<f, A>, B>, C>) hk;
  }

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B, C, D> Hk<Hk<Hk<Hk<f, A>, B>, C>, D> covary4(Hk<? extends Hk<? extends Hk<? extends Hk<f, A>, B>, C>, D>
                                                                          hk) {
    return (Hk<Hk<Hk<Hk<f, A>, B>, C>, D>) hk;
  }

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B, C, D, E> Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E> covary5(Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<f, A>, B>, C>, D>, E> hk) {
    return (Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>) hk;
  }

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B, C, D, E, F> Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F> covary6(Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<f, A>, B>, C>, D>, E>, F> hk) {
    return (Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F>) hk;
  }

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B, C, D, E, F, G> Hk<Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F>, G> covary7(Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<f, A>, B>, C>, D>, E>, F>, G> hk) {
    return (Hk<Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F>, G>) hk;
  }

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B, C, D, E, F, G, H> Hk<Hk<Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F>, G>, H> covary8(Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<f, A>, B>, C>, D>, E>, F>, G>, H> hk) {
    return (Hk<Hk<Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F>, G>, H>) hk;
  }

  /**
   * Covary on type constructor witness to normalize to KindedJ encoding.
   */
  @SuppressWarnings("unchecked")
  public static <f, A, B, C, D, E, F, G, H, I> Hk<Hk<Hk<Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F>, G>, H>, I> covary9(Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<? extends Hk<f, A>, B>, C>, D>, E>, F>, G>, H>, I> hk) {
    return (Hk<Hk<Hk<Hk<Hk<Hk<Hk<Hk<Hk<f, A>, B>, C>, D>, E>, F>, G>, H>, I>) hk;
  }

}
