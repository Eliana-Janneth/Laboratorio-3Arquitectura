import { IAuditoriaNota } from 'app/shared/model/auditoria-nota.model';
import { IGrupo } from 'app/shared/model/grupo.model';

export interface IProfesor {
  id?: number;
  profesorId?: number;
  nombre?: string;
  tipo?: string;
  correoElectronico?: string;
  usuarioPortal?: string;
  auditoriasNotas?: IAuditoriaNota[] | null;
  grupos?: IGrupo | null;
}

export const defaultValue: Readonly<IProfesor> = {};
