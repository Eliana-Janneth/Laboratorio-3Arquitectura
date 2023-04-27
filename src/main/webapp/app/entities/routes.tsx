import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Estudiante from './estudiante';
import Profesor from './profesor';
import Curso from './curso';
import SemestreAcademico from './semestre-academico';
import Grupo from './grupo';
import Actividad from './actividad';
import GrupoEstudiante from './grupo-estudiante';
import Nota from './nota';
import AuditoriaNota from './auditoria-nota';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="estudiante/*" element={<Estudiante />} />
        <Route path="profesor/*" element={<Profesor />} />
        <Route path="curso/*" element={<Curso />} />
        <Route path="semestre-academico/*" element={<SemestreAcademico />} />
        <Route path="grupo/*" element={<Grupo />} />
        <Route path="actividad/*" element={<Actividad />} />
        <Route path="grupo-estudiante/*" element={<GrupoEstudiante />} />
        <Route path="nota/*" element={<Nota />} />
        <Route path="auditoria-nota/*" element={<AuditoriaNota />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
