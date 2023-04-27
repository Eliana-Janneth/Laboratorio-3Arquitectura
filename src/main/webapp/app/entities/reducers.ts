import estudiante from 'app/entities/estudiante/estudiante.reducer';
import profesor from 'app/entities/profesor/profesor.reducer';
import curso from 'app/entities/curso/curso.reducer';
import semestreAcademico from 'app/entities/semestre-academico/semestre-academico.reducer';
import grupo from 'app/entities/grupo/grupo.reducer';
import actividad from 'app/entities/actividad/actividad.reducer';
import grupoEstudiante from 'app/entities/grupo-estudiante/grupo-estudiante.reducer';
import nota from 'app/entities/nota/nota.reducer';
import auditoriaNota from 'app/entities/auditoria-nota/auditoria-nota.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  estudiante,
  profesor,
  curso,
  semestreAcademico,
  grupo,
  actividad,
  grupoEstudiante,
  nota,
  auditoriaNota,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
