package co.s4ncampus.fpwithscala.users.domain

import cats.Applicative
import cats.data.EitherT

class UserValidationInterpreter[F[_]: Applicative](repository: UserRepositoryAlgebra[F])
    extends UserValidationAlgebra[F] {
  def doesNotExist(user: User): EitherT[F, UserAlreadyExistsError, Unit] = 
    repository.findByLegalId(user.legalId).map(UserAlreadyExistsError).toLeft(())

  def notExist(user:User): EitherT[F, UserNotExistError, Unit]= {
    repository.findByLegalId(user.legalId).map(UserNotExistError).toLeft(())
  }
}

object UserValidationInterpreter {
  def apply[F[_]: Applicative](repository: UserRepositoryAlgebra[F]) =
    new UserValidationInterpreter[F](repository)
}