package co.s4ncampus.fpwithscala.users.domain

import cats.data._
import cats.Monad

class UserService[F[_]](repository: UserRepositoryAlgebra[F], validation: UserValidationAlgebra[F]) {
  def create(user: User)(implicit M: Monad[F]): EitherT[F, UserAlreadyExistsError, User] =
    for {
      _ <- validation.doesNotExist(user)
      saved <- EitherT.liftF(repository.create(user))
    } yield saved

  def get(legalId: String): OptionT[F, User] =
      repository.findByLegalId(legalId)
  
  def update(user: User, legalId: String): F[Int] = 
      repository.update(user, legalId)
}
object UserService{
  def apply[F[_]](
                 repositoryAlgebra: UserRepositoryAlgebra[F],
                 validationAlgebra: UserValidationAlgebra[F],
                 ): UserService[F] =
    new UserService[F](repositoryAlgebra, validationAlgebra)
}